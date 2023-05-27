Vue.component("registerPage",{
	data:function(){
		return{
			
		}
	},
	template: `
	<div>
		<h1>Register here</h1>
		<form>
			<label>Username</label>
			<input type="text"><br>
			<label>Password</label>
			<input type="password"><br>
			<label>Confirm password</label>
			<input type="password"><br>
			<label>First name</label>
			<input type="text"><br>
			<label>Last name</label>
			<input type="text"><br>
			<label>Gender</label><br>
			<input type="radio" name="gender" value="M" id="male">
			<label>Male</label><br>
			<input type="radio" name="gender" value="F" id="female">
			<label>Female</label><br>
			<label>Date of birth</label>
			<input type="date"><br><br>
			<input type="submit" value="Register!">
		</form>
	</div>
	`,
	mounted(){
		
	},
	methods:{
		
	}
})