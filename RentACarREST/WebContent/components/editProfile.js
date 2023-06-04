Vue.component("editProfile",{
	data:function(){
		return{
			id: null,
			customer: {id: null,firstName: null, 
			lastName: null, gender: null, role: null, dateOfBirth: null},
			date: null,
			errorMessage: ''
		}
	},
	template: `
	
	<div>
		<h1 style="width:180px; margin:auto">Edit your profile</h1>
		<div style="width:480px; margin:auto; font-size:18px">
			<form>
				<div style="border:1px solid black; margin:5px">
					<div style="margin:10px">
						<label>First name*</label>
						<input type="text" v-model="customer.firstName" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Last name*</label>
						<input type="text" v-model="customer.lastName" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Gender*</label>
						<input type="radio" name="gender" value="male" id="male" v-model="customer.gender" style="float:right">
						<label style="float:right">Male</label><br>
						<input type="radio" name="gender" value="female" id="female" v-model="customer.gender" style="float:right">
						<label style="float:right">Female</label><br>
					</div>
					<div style="margin:10px">
						<label>Date of birth*</label>
						<input type="date" v-model="customer.dateOfBirth" style="float:right; font-size:17px"><br>
					</div>
				</div>
				<div style="width:280px; margin:auto">
					<input type="submit" value="Submit!" v-on:click="editCustomer" style="background-color:powderblue; font-size:20px; display:inline-block">
					<button v-on:click="goBack" style="background-color:powderblue; font-size:20px; float:right; display:inline-block">Go back</button>
				</div>
				<br>
				<p style="color:red; width:200px; margin:auto">{{errorMessage}}</p>
			</form>
		</div>
	</div>
	`,
	mounted: function() {
     this.loadData()
     
     setTimeout(() => {
        this.formatDate()
      }, 1000);
  },
	methods:{
		editCustomer: function(){
			event.preventDefault()
			this.errorMessage = ''
			if(this.customer.firstName.trim().length == 0){
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
			let conf = confirm("Are you sure? Click ok to confirm.")
			if(conf){
			axios.put("rest/customers/edit", this.customer).then(response => ( router.push(`/user/`)))
			}
		},
		loadData: function(){
			axios.get("rest/currentUser")
			.then(response =>	
				this.customer = response.data)
		},
		formatDate: function(){
			let date = new Date(this.customer.dateOfBirth)
			let day = date.getDate()
			let month = date.getMonth()+1
			let year = date.getFullYear()
			let zeroAtDay = ''
			let zeroAtMonth = ''
			if(day<10){
				zeroAtDay = '0'
			}
			if(month<10){
				zeroAtMonth = '0'
			}
			this.customer.dateOfBirth = year+'-'+zeroAtMonth+month+'-'+zeroAtDay+day
		},
		goBack: function(){
			event.preventDefault()
			router.push(`/user/`)
		}
	}
})