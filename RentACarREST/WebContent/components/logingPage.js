Vue.component("logingPage",{
	data:function(){
		return{
			user: {id:0, username:"", password:"", role:null}
		}
	},
	template: `
	<div>
		<form>
		<label>Username</label>
		<input type="text" v-model="user.username">
		<label>Password</label>
		<input type="password" v-model="user.password">
		<input type="submit" v-on:click="loggin">
		</form>
		<label>Don't have an account?</label>
		<a href="" v-on:click="register">Register now</a>
		<rentACarTemplate></rentACarTemplate>
	</div>
	`,
	mounted(){
	},
	methods:{
		register: function(){
			event.preventDefault()
			router.push(`/register/`)
		},
		loggin: function(){
			event.preventDefault()
			axios.post("rest/login/", this.user)
			.then(response => (router.push(`/user/`) ))
			.catch(error => alert('wrong username and/or password'))
		}
	}
})