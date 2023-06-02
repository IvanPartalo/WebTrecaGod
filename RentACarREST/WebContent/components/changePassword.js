Vue.component("changePassword",{
	data:function(){
		return{
			role: null,
			id: null,
			oldPassword: '',
			newPassword: '',
			newPasswordConfirmation: '',
			errorMessage: '',
			success: null
		}
	},
	template: `
	
	<div>
		<h1 style="width:220px; margin:auto">Change password</h1>
		<div style="width:480px; margin:auto; font-size:18px">
			<form>
				<div style="border:1px solid black; margin:5px">
					<div style="margin:10px">
						<label>Old password*</label>
						<input type="password" v-model="oldPassword" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>New password*</label>
						<input type="password" v-model="newPassword" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Confirm password*</label>
						<input type="password" v-model="newPasswordConfirmation" style="float:right; font-size:17px"><br>
					</div>
				</div>
				<div style="width:280px; margin:auto">
					<input type="submit" value="Change" v-on:click="changePassword" style="background-color:powderblue; font-size:20px; display:inline-block">
					<button v-on:click="goBack" style="background-color:powderblue; font-size:20px; float:right; display:inline-block">Go back</button>
				</div>
				<br>
				<p style="color:red; width:200px; margin:auto">{{errorMessage}}</p>
			</form>
		</div>
	</div>
	`,
	mounted: function() {
    	this.id = this.$route.params.id
    	this.role = this.$route.params.customer
  },
	methods:{
		changePassword: function(){
			event.preventDefault()
			this.errorMessage = ''
			if(this.newPassword.trim().length == 0){
				this.errorMessage = "New password can't be empty!"
				return;
			}
			else if(this.newPassword != this.newPasswordConfirmation){
				this.errorMessage = "Passwords have to match!"
				return;
			}
			let conf = confirm("Are you sure? Click ok to confirm.")
			if(conf){
			let success
			axios.put("rest/customers/changepassword/"+this.id+"/"+this.oldPassword+"/"+this.newPassword)
			.then(response => this.success = response.data)
			setTimeout(() => {
	        this.checkIfSucceded()
	      	}, 500);
			
			}
		},
		checkIfSucceded: function(){
			if(this.success){
				router.push(`/${this.role}/${this.id}`)
			}
			else{
				this.errorMessage = "Old password is not correct!"
			}
		},
		goBack: function(){
			event.preventDefault()
			router.push(`/${this.role}/${this.id}`)
		}
	}
})