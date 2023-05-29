Vue.component("registerPage",{
	data:function(){
		return{
			customer: {id:0, username:"", password:"", firstName:"", lastName:"", gender:"", role:"customer", dateOfBirth:null}
		}
	},
	template: `
	<div>
		<h1>Register here</h1>
		<form>
			<label>Username</label>
			<input type="text" v-model="customer.username"><br>
			<label>Password</label>
			<input type="password" v-model="customer.password"><br>
			<label>Confirm password</label>
			<input type="password" v-model="customer.password"><br>
			<label>First name</label>
			<input type="text" v-model="customer.firstName"><br>
			<label>Last name</label>
			<input type="text" v-model="customer.lastName"><br>
			<label>Gender</label><br>
			<input type="radio" name="gender" value="male" id="male" v-model="customer.gender">
			<label>Male</label><br>
			<input type="radio" name="gender" value="female" id="female" v-model="customer.gender">
			<label>Female</label><br>
			<label>Date of birth</label>
			<input type="date" v-model="customer.dateOfBirth"><br><br>
			<input type="submit" value="Register!" v-on:click="registerCustomer">
		</form>
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