Vue.component("rentACarCreate",{
	data:function(){
		return{
			rentACarDTO: {name:"", address:"", longitude:"", latitude:"", beginWorkTime:null, endWorkTime:null},
			errorMessage: ""
		}
	},
	template: `
	<div >
		<h1 style="width:400px; margin:auto">Create new rent a car</h1>
		<div style="width:480px; margin:auto; font-size:18px">
			<form>
				<div style="border:1px solid black; margin:5px">
					<div style="margin:10px">
						<label>Name*</label>
						<input type="text" v-model="rentACarDTO.name" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Address*</label>
						<input type="text" v-model="rentACarDTO.address" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>longitude*</label>
						<input v-model="rentACarDTO.longitude" type="text" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>latitude*</label>
						<input type="text" v-model="rentACarDTO.latitude" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Begin work time*</label>
						<input type="time" v-model="rentACarDTO.beginWorkTime" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>End work time*</label>
						<input type="time" v-model="rentACarDTO.endWorkTime" style="float:right; font-size:17px"><br>
					</div>
				</div>
				<div style="width:80px; margin:auto">
					<input type="submit" value="Create!" v-on:click="create" style="background-color:powderblue; font-size:20px">
				</div>
				<br>
				<p v-if="errorMessage.length" style="color:red; width:200px; margin:auto">{{errorMessage}}</p>
			</form>
		</div>
	</div>
	`,
	mounted(){
	},
	methods:{
		create: function(){
			event.preventDefault()
			/*this.errorMessage = ""
			if(this.customer.username.trim().length == 0){
				this.errorMessage = "Enter username!"
				return;
			}
			else if(this.customer.password.trim().length == 0){
				this.errorMessage = "Enter password!"
				return;
			}
			else if(this.confirmedPassword.trim().length == 0){
				this.errorMessage = "Confirm passwor!"
				return;
			}
			else if(this.customer.password != this.confirmedPassword){
				this.errorMessage = "Passwords have to match!"
				return;
			}
			else if(this.customer.firstName.trim().length == 0){
				this.errorMessage = "Enter first name!"
				return;
			}
			else if(this.customer.lastName.trim().length == 0){
				this.errorMessage = "Enter last name!"
				return;
			}
			else if(!this.customer.dateOfBirth){
				this.errorMessage = "Choose birthday!"
				return;
			}
			*/
			axios.post("rest/rentacar/", this.rentACarDTO)
			.then(response => ( router.push(`/user/`)))
		}
	}
})