Vue.component("profilePage",{
	data:function(){
		return{
			id: null
		}
	},
	template: `
	<div>
		<h1>Profile page</h1>
		<button v-on:click="edit">Edit profile</button>
	</div>
	`,
	mounted: function() {
     this.id = this.$route.params.id
    },
	methods:{
		edit: function(){
			router.push(`/edit/${this.id}`)
		}
	}
})