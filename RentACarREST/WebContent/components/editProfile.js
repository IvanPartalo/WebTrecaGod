Vue.component("editProfile",{
	data:function(){
		return{
			id: null,
			customer: {id: null, username: null, password: null, firstName: null, 
			lastName: null, gender: null, role: null, dateOfBirth: null},
			date: null
		}
	},
	template: `
	<div>
		<h1>Edit your profile</h1>
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
			<input type="submit" value="Submit!" v-on:click="editCustomer">
		</form>
		<p>{{date}}</p>
	</div>
	`,
	mounted: function() {
     this.loadData()
     
     setTimeout(() => {
        this.formatDate()
      }, 1000);
  },
	methods:{
		editCustomer: function(){
			event.preventDefault()
			axios.put("rest/customers/edit", this.customer).then(response => ( router.push(`/customer/${this.customer.id}`)))
		},
		loadData: function(){
			this.id = this.$route.params.id
			axios.get("rest/customers/"+this.id)
			.then(response =>	
				this.customer = response.data
			)	
		},
		formatDate: function(){
			let date = new Date(this.customer.dateOfBirth)
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
			this.customer.dateOfBirth = year+'-'+zeroAtMonth+month+'-'+zeroAtDay+day
			console.log(this.customer.dateOfBirth)	
		}
	}
})