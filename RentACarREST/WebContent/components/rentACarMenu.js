Vue.component("rentACarMenu",{
	data:function(){
		return{
		}
	},
	template: `
	<div>
		<div style="background-color:powderblue; font-size:20px">
			<a href="" v-on:click="backToProfile" style="margin-left:15px">Go back to profile</a>
			<a href="" v-on:click="logOut" style="float:right; margin-right:30px">Log out</a>
		</div>
	</div>
	
	`,
	mounted(){
	},
	methods:{
		logOut: function(){
			event.preventDefault()
			axios.post("rest/logout")
			.then(response => router.push(`/`))
		},
		backToProfile: function(){
			event.preventDefault()
			router.push(`/user/`)
		}
	}
})