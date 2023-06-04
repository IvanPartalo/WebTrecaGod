Vue.component("profilePage",{
	data:function(){
		return{
			id: null,
			customer: {id: null, username: null, password: null, firstName: null, 
			lastName: null, gender: null, role: null, dateOfBirth: null}
		}
	},
	template: `
	<div>
		<div style="background-color:powderblue; font-size:20px">
			<a href="" v-on:click="rentACar" style="margin-left:15px">Rent a car shops</a>
			<a href="" v-on:click="edit" style="margin-left:15px">Edit profile</a>
			<a href="" v-on:click="changePassword" style="margin-left:15px">Change password</a>
			<a href="" v-on:click="logOut" style="float:right; margin-right:30px">Log out</a>
		</div>
	    <div style="border:1px solid black; padding-left: 5px;">
			<h1 style="color:red;">Profile page</h1>
	        <h2>{{customer.firstName}} {{customer.lastName}}</h2>
	    <div style="padding-left: 5px; font-size:20px">
	        <label>Username:</label>
	        <label>{{customer.username}}</label>
	    </div>
	    <div style="padding-left: 5px;">
	        <label>Gender:</label>
	        <label>{{customer.gender}}</label><br>
	        <label>Date of birth:</label>
	        <label>{{customer.dateOfBirth}}</label><br>
	        <label>Role:</label>
	        <label>{{customer.role}}</label><br>
	    </div>
	    </div>
	</div>
	`,
	mounted: function() {
    	axios.get("rest/currentUser")
			.then(response =>	
				this.customer = response.data)
		setTimeout(() => {
        	this.formatDate()
      	}, 1000)
    },
	methods:{
		edit: function(){
			event.preventDefault()
			router.push(`/user/edit/`)
		},
		logOut: function(){
			event.preventDefault()
			axios.post("rest/logout")
			.then(response =>	router.push(`/`))
		},
		changePassword: function(){
			event.preventDefault()
			router.push(`/user/changepassword/`)
		},
		rentACar: function(){
			event.preventDefault()
			router.push(`/rentacar/`)
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