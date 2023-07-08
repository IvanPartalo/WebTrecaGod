Vue.component("suspects",{
	data:function(){
		return{
			supectUsers : null
		}
	},
	template: `
	<div>
		<h3 style="text-align:center">Suspect customers (canceled 5 or more times in the last month)</h3>
		<br>
		<table style="margin-right:auto; margin-left:auto; font-size:18px; border-collapse: collapse; text-align: center">
			<tr style="border: solid thin; background-color:#8083c9">
				<th class="first-td">Username</th>
				<th class="td-users">Name</th>
				<th class="td-users">Surname</th>
				<th class="td-users">Points</th>
				<th class="td-users">Customer type</th>
				<th class="td-users">Block</th>
			</tr>
			<tr v-for="u in supectUsers" style="border: solid thin; margin-top:10px">
				<td class="first-td"  style="width:100px">{{u.username}}</td>
				<td class="td-users">{{u.firstName}}</td>
				<td class="td-users">{{u.lastName}}</td>
				<td class="td-users">{{u.collectedPoints}}</td>
				<td class="td-users">{{u.customerType.name}}</td>
				<td class="td-users">
					<button v-on:click="Block(u.id)">block</button>
				</td>
			</tr>
		</table>
	</div>
	`,
	mounted: function() {
		axios.get('rest/users/suspects')
		.then(response => this.supectUsers = response.data)
    },
	methods:{
		Block: function(id){
			axios.post("rest/users/block/" + id)
			.then(response => {
				axios.get('rest/users/suspects')
				.then(response => this.supectUsers = response.data)
			})
		}
	}
})