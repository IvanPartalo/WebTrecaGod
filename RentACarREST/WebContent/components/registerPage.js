Vue.component("registerPage",{
	data:function(){
		return{
			customer: {id:0, username:"", password:"", firstName:"", lastName:"", gender:"male", role:"customer", dateOfBirth:null}
		}
	},
	template: `
	<div >
		<h1 style="width:200px; margin:auto">Register here</h1>
		<div style="width:340px; margin:auto">
			<form >
				<div style="border:1px solid black; margin:5px">
					<div style="margin:5px">
						<label>Username*</label>
						<input type="text" v-model="customer.username" placeholder="Enter username" style="float:right"><br>
					</div>
					<div style="margin:5px">
						<label>Password*</label>
						<input type="password" v-model="customer.password" placeholder="Enter password" style="float:right"><br>
					</div>
					<div style="margin:5px">
						<label>Confirm password*</label>
						<input type="password" placeholder="Reenter password" style="float:right"><br>
					</div>
					<div style="margin:5px">
						<label>First name*</label>
						<input type="text" v-model="customer.firstName" placeholder="Enter your name" style="float:right"><br>
					</div>
					<div style="margin:5px">
						<label>Last name*</label>
						<input type="text" v-model="customer.lastName" placeholder="Enter your last name" style="float:right"><br>
					</div>
					<div style="margin:5px">
						<label>Gender*</label>
						<input type="radio" name="gender" value="male" id="male" v-model="customer.gender" style="float:right">
						<label style="float:right">Male</label><br>
						<input type="radio" name="gender" value="female" id="female" v-model="customer.gender" style="float:right">
						<label style="float:right">Female</label><br>
					</div>
					<div style="margin:5px">
						<label>Date of birth*</label>
						<input type="date" v-model="customer.dateOfBirth" style="float:right"><br><br>
					</div>
				</div>
				<div style="width:80px; margin:auto">
					<input type="submit" value="Register!" v-on:click="registerCustomer" style="background-color:powderblue;">
				</div>
			</form>
		</div>
	</div>
	`,
	mounted(){
	},
	methods:{
		registerCustomer: function(){
			event.preventDefault()
			axios.post("rest/customers/", this.customer).then(response => ( router.push(`/`)))
		}
	}
})