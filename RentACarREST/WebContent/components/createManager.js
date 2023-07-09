Vue.component("createManager",{
	data:function(){
		return{
			manager: {id:0, username:"", password:"", firstName:"", lastName:"", gender:"male", role:"manager", dateOfBirth:null},
			errorMessage: "",
			confirmedPassword : ""
		}
	},
	template: `
	<div >
		<h1 style="width:200px; margin:auto">Create new manager</h1>
		<div style="width:480px; margin:auto; font-size:18px">
			<form>
				<div style="border:1px solid black; margin:5px">
					<div style="margin:10px">
						<label>Username*</label>
						<input id="usernameInput" type="text" v-model="manager.username" placeholder="Enter username" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Password*</label>
						<input id="passwordInput" type="password" v-model="manager.password" placeholder="Enter password" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Confirm password*</label>
						<input v-model="confirmedPassword" type="password" placeholder="Reenter password" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>First name*</label>
						<input id="firstNameInput"  type="text" v-model="manager.firstName" placeholder="Enter your name" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Last name*</label>
						<input id="lastNameInput"  type="text" v-model="manager.lastName" placeholder="Enter your last name" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Gender*</label>
						<input type="radio" name="gender" value="male" id="male" v-model="manager.gender" style="float:right">
						<label style="float:right">Male</label><br>
						<input type="radio" name="gender" value="female" id="female" v-model="manager.gender" style="float:right">
						<label style="float:right">Female</label><br>
					</div>
					<div style="margin:10px">
						<label>Date of birth*</label>
						<input id="dateInput"  type="date" v-model="manager.dateOfBirth" style="float:right; font-size:17px"><br>
					</div>
				</div>
				<div style="width:80px; margin:auto">
					<input type="submit" value="Register!" v-on:click="registerManager" style="background-color:powderblue; font-size:20px">
				</div>
				<br>
				<p v-if="errorMessage.length" style="color:red; width:200px; margin:auto">{{errorMessage}}</p>
			</form>
		</div>
	</div>
	`,
	mounted(){
		let todaysDate = new Date()
		let year = todaysDate.getFullYear() - 18
		let month = todaysDate.getMonth()
		let day = todaysDate.getDate()
		document.getElementById("dateInput").max = new Date(year, month, day).toISOString().split("T")[0];
	},
	methods:{
		registerManager: function(){
			event.preventDefault()
			this.errorMessage = ""
			if(this.manager.username.trim().length == 0){
				this.errorMessage = "Enter username!"
				return;
			}
			else if(this.manager.password.trim().length == 0){
				this.errorMessage = "Enter password!"
				return;
			}
			else if(this.confirmedPassword.trim().length == 0){
				this.errorMessage = "Confirm password!"
				return;
			}
			else if(this.manager.password != this.confirmedPassword){
				this.errorMessage = "Passwords have to match!"
				return;
			}
			else if(this.manager.firstName.trim().length == 0){
				this.errorMessage = "Enter first name!"
				return;
			}
			else if(this.manager.lastName.trim().length == 0){
				this.errorMessage = "Enter last name!"
				return;
			}
			else if(!this.manager.dateOfBirth){
				this.errorMessage = "Choose birthday!"
				return;
			}
			
			axios.post("rest/rentacar/newmanager", this.manager)
			.then(response => ( router.push(`/user/`))).catch(error => this.errorMessage = error.response.data)
		}
	}
})