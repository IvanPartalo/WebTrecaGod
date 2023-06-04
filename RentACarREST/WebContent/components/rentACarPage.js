Vue.component("rentACar",{
	data:function(){
		return{
			rentACars: null
		}
	},
	template: `
	<div>
	    <rentACarMenu></rentACarMenu>
	    <br>
	    <rentACarTemplate></rentACarTemplate>
	 </div>
	`,
	methods:{
		
	}
})