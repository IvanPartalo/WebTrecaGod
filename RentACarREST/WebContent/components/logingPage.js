Vue.component("logingPage",{
	data:function(){
		return{
			
		}
	},
	template: `
	<div>
		<form>
		<label>Username</label>
		<input type="text">
		<label>Password</label>
		<input type="password">
		<input type="submit" v-on:click="loggin">
		</form>
		<label>Don't have an account?</label>
		<a href="" v-on:click="register">Register now</a>
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
		}
	}
})