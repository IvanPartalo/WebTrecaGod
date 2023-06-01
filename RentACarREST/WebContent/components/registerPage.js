Vue.component("registerPage",{
	data:function(){
		return{
			customer: {id:0, username:"", password:"", firstName:"", lastName:"", gender:"male", role:"customer", dateOfBirth:null},
			errorMessage: "",
			confirmedPassword : ""
		}
	},
	template: `
	<div >
		<h1 style="width:200px; margin:auto">Register here</h1>
		<div style="width:480px; margin:auto; font-size:18px">
			<form>
				<div style="border:1px solid black; margin:5px">
					<div style="margin:10px">
						<label>Username*</label>
						<input id="usernameInput" type="text" v-model="customer.username" placeholder="Enter username" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Password*</label>
						<input id="passwordInput" type="password" v-model="customer.password" placeholder="Enter password" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Confirm password*</label>
						<input v-model="confirmedPassword" type="password" placeholder="Reenter password" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>First name*</label>
						<input id="firstNameInput"  type="text" v-model="customer.firstName" placeholder="Enter your name" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Last name*</label>
						<input id="lastNameInput"  type="text" v-model="customer.lastName" placeholder="Enter your last name" style="float:right; font-size:17px"><br>
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
						<input id="dateInput"  type="date" v-model="customer.dateOfBirth" style="float:right; font-size:17px"><br>
					</div>
				</div>
				<div style="width:80px; margin:auto">
					<input type="submit" value="Register!" v-on:click="registerCustomer" style="background-color:powderblue; font-size:20px">
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
		registerCustomer: function(){
			event.preventDefault()
			this.errorMessage = ""
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
			
			axios.post("rest/customers/", this.customer).then(response => ( router.push(`/`))).catch(error => this.errorMessage = error.response.data)
		}
	}
})