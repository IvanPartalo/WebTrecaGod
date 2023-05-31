Vue.component("registerPage",{
	data:function(){
		return{
			customer: {id:0, username:"", password:"", firstName:"", lastName:"", gender:"male", role:"customer", dateOfBirth:null},
			errorMessage: ""
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
						<input id="confirmPasswordInput" type="password" placeholder="Reenter password" style="float:right; font-size:17px"><br>
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
				<p id="errorMessage" style="color:red; width:200px; margin:auto" hidden=true>{{errorMessage}}</p>
			</form>
		</div>
	</div>
	`,
	mounted(){
	},
	methods:{
		registerCustomer: function(){
			event.preventDefault()
			let userName = document.getElementById("usernameInput").value 
			let password = document.getElementById("passwordInput").value 
			let confirmedPassword = document.getElementById("confirmPasswordInput").value 
			let firstName = document.getElementById("firstNameInput").value 
			let lastName = document.getElementById("lastNameInput").value 
			let date = document.getElementById("dateInput").value 
			if(userName.trim().length == 0){
				this.errorMessage = "Enter username!"
				document.getElementById("errorMessage").hidden = false
				return;
			}
			else if(password.trim().length == 0){
				this.errorMessage = "Enter password!"
				document.getElementById("errorMessage").hidden = false
				return;
			}
			else if(confirmedPassword.trim().length == 0){
				this.errorMessage = "Confirm passwor!"
				document.getElementById("errorMessage").hidden = false
				return;
			}
			else if(firstName.trim().length == 0){
				this.errorMessage = "Enter first name!"
				document.getElementById("errorMessage").hidden = false
				return;
			}
			else if(lastName.trim().length == 0){
				this.errorMessage = "Enter last name!"
				document.getElementById("errorMessage").hidden = false
				return;
			}
			else if(!date){
				this.errorMessage = "Choose birthday!"
				document.getElementById("errorMessage").hidden = false
				return;
			}
			if(password != confirmedPassword){
				this.errorMessage = "Passwords have to match!"
				document.getElementById("errorMessage").hidden = false
				return;
			}
			axios.post("rest/customers/", this.customer).then(response => ( router.push(`/`)))
		}
	}
})