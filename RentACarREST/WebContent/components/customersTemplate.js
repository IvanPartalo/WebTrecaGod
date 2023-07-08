Vue.component("customersTemplate",{
	data:function(){
		return{
			users : null,
			firstName: '',
			lastName: '',
			username: '',
			sort: 'None',
			sortType: 'ascending',
			ascending: 1,
			role: 'All',
			customerType: 'All',
			filterApplied: false
		}
	},
	template: `
	<div>
		<h1 style="text-align:center">USERS</h1>
		<dialog id="dijalog" ref="dijalog" style="padding: 20px; margin-left:auto; margin-bottom:20px; margin-right:20px; position:absolute">
			<h4>Filters</h4>
			<label>Role: </label>
			<select v-model="role" style="margin-left:10px">
				<option>All</option>
				<option>Customer</option>
				<option>Manager</option>
				<option>Administrator</option>
			</select>
			<label>Customer type</label>
			<select v-model="customerType" style="margin-left:10px">
				<option>All</option>
				<option>Golden</option>
				<option>Silver</option>
				<option>Bronze</option>
				<option>Regular</option>
			</select>
			<br><br>
			<button v-on:click="closeDialog">Apply</button>
		</dialog>
		<h3 style="margin-left:20px">Search for specific user</h3>
		<div style="margin-left: 40px">
			<div style="float:left">
				<label>Firts name: </label><input type="text" v-model="firstName" style="margin-right:20px">
				<label>Last name: </label><input type="text" v-model="lastName" style="margin-right:20px">
				<label>Username: </label><input type="text" v-model="username" style="margin-right:20px">
				<label style="margin-left:20px">Sort</label>
				<select v-model="sort" style="margin-left:10px">
					<option>None</option>
					<option>First name</option>
					<option>Last name</option>
					<option>Username</option>
					<option>Collected points</option>
				</select>
			</div>
			<div style="float:left; margin-left: 10px">
				<div>
					<input type="radio" id="asc" name="sort_type" value="ascending" v-model="sortType">	
					<label for="asc">ascending</label>
				</div>
				<div>
					<input type="radio" id="desc" name="sort_type" value="descending" v-model="sortType">
					<label for="desc">descending</label>
				</div>
			</div>
			<div style="float:left">
				<button style="margin-left:20px">Filters</button>
			</div>
		</div>
		<br>
		<table style="margin-right:auto; margin-left:auto; font-size:18px; border-collapse: collapse; text-align: center">
			<tr style="border: solid thin; background-color:#8083c9">
				<th class="first-td" style="width:100px">Username</th>
				<th class="td-users">Name</th>
				<th class="td-users">Surname</th>
				<th class="td-users">Gender</th>
				<th class="td-users">Date of birth</th>
				<th class="td-users">Role</th>
				<th class="td-users">Points</th>
				<th class="td-users">Customer type</th>
				<th class="td-users">Block</th>
			</tr>
			<tr v-for="u in usersList" style="border: solid thin; margin-top:10px">
				<td class="first-td"  style="width:100px">{{u.username}}</td>
				<td class="td-users">{{u.firstName}}</td>
				<td class="td-users">{{u.lastName}}</td>
				<td class="td-users">{{u.gender}}</td>
				<td class="td-users">{{u.dateOfBirth}}</td>
				<td class="td-users">{{u.role}}</td>
				<td class="td-users">{{u.collectedPoints}}</td>
				<td class="td-users">{{u.customerType}}</td>
				<td v-if="u.role != 'administrator'" class="td-users">
					<div v-if="u.blocked == false">
						<button v-on:click="Block(u.id)">block</button>
					</div>
					<div v-else>
						<label>User is blocked</label>
					</div>
				</td>
			</tr>
		</table>
	</div>
	`,
	mounted: function() {
		axios.get('rest/users/userstoshow')
		.then(response => this.users = response.data)
		setTimeout(() => {
        	this.formatTableData()
      	}, 200)
    },
    computed:{
		usersList(){
			let filteredList = this.users
			
			//sortiranje
			if(this.sortType == 'ascending'){
				this.ascending = 1
			}
			else{
				this.ascending = -1
			}
			if(this.sort != 'None'){
				if (this.sort == "First name"){
					filteredList.sort((a, b) => {
					  const A = a.firstName.toLowerCase();
					  const B = b.firstName.toLowerCase();
					  return this.moveElements(A, B)
					});
				}
				if (this.sort == "Last name"){
					filteredList.sort((a, b) => {
					  const A = a.lastName.toLowerCase();
					  const B = b.lastName.toLowerCase();
					  return this.moveElements(A, B)
					});
				}
				if (this.sort == "Username"){
					filteredList.sort((a, b) => {
					  const A = a.username.toLowerCase();
					  const B = b.username.toLowerCase();
					  return this.moveElements(A, B)
					});
				}
				if (this.sort == "Collected points"){
					filteredList.sort((a, b) => {
					  const A = a.collectedPoints;
					  const B = b.collectedPoints;
					  return this.moveElements(A, B)
					});
				}
			}
			
			//pretraga
			if(this.firstName || this.lastName || this.username || this.filterApplied){
				if(this.filterApplied){
					if (this.role != "All"){
						filteredList = filteredList.filter((user) => user.role == this.role.toLowerCase())
					}
					if (this.customerType != "All"){
						filteredList = filteredList.filter((user) => user.customerType == this.customerType.toLowerCase())
					}
				}
				if(this.firstName != ""){
					filteredList = filteredList.filter((user) => user.firstName.toLowerCase().includes(
					this.firstName.toLowerCase()))
				}
				if(this.lastName != ""){
					filteredList = filteredList.filter((user) => user.lastName.toLowerCase().includes(
					this.lastName.toLowerCase()))
				}
				if(this.username != ""){
					filteredList = filteredList.filter((user) => user.username.toLowerCase().includes(
					this.username.toLowerCase()))
				}
				return filteredList
			}else{
				return this.users
			}
		}
	},
	methods:{
		formatTableData: function(){
			this.users.forEach((user) => {
				let date = new Date(user.dateOfBirth)
				let day = date.getDate()
				let month = date.getMonth()+1
				let year = date.getFullYear()
				let zeroAtDay = ''
				let zeroAtMonth = ''
				if(day<10){
					zeroAtDay = '0'
				}
				if(month<10){
					zeroAtMonth = '0'
				}
				user.dateOfBirth = year+'-'+zeroAtMonth+month+'-'+zeroAtDay+day
				if(!user.customerType){
					user.customerType = '/'
				}
			});
		},
		moveElements: function(a, b){
			if (a < b) {
			    return -1*this.ascending;
			}
			if (a > b) {
				return 1*this.ascending;
			}
			return 0;
		},
		showDialog: function(){
			this.filterApplied = false
			this.$refs.dijalog.showModal()
		},
		closeDialog: function(){
			this.filterApplied = true
			this.$refs.dijalog.close()
		},
		Block: function(id){
			axios.post("rest/users/block/" + id)
			.then(response => {
				axios.get('rest/users/userstoshow')
				.then(response => this.users = response.data)
				setTimeout(() => {
		        	this.formatTableData()
		      	}, 200)
			})
		}
	}
})