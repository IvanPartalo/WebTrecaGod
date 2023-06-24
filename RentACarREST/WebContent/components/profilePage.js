Vue.component("profilePage",{
	data:function(){
		return{
			notAdmin: false,
			notManager: false,
			isCustomer: false,
			id: null,
			user: {id: null, username: null, password: null, firstName: null, 
			lastName: null, gender: null, role: null, dateOfBirth: null, collectedPoints:null, 
			customerType:{name:null, discount:null, requiredPoints:null}}
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
		<div class="row">
			<div class="column">
			    <div style="border:1px solid black; padding-left: 5px;border-radius: 30px; margin:1%;text-align: center;background-color:#8083c9">
					<h1 style="color:red;">Personal info</h1>
			        <h2>{{user.firstName}} {{user.lastName}}</h2>
				    <div style="padding-left: 5px; font-size:20px">
				        <label>Username:</label>
				        <b><label>{{user.username}}</label></b>
				    </div>
				    <div style="padding-left: 5px; font-size:20px">
				        <label>Gender:</label>
				        <b><label>{{user.gender}}</label><br></b>
				        <label>Date of birth:</label>
				        <b><label>{{user.dateOfBirth}}</label><br></b>
				        <label>Role:</label>
				        <b><label>{{user.role}}</label><br></b>
				    </div>
				     <div v-if="isCustomer" style="padding-left: 5px; font-size:20px">
				    	<label>Collected points:</label>
				    	<b><label>{{user.collectedPoints}}</label><br></b>
				    	<label>Customer type:</label>
				    	<b><label>{{user.customerType.name}}</label><br></b>
				    </div>
				    <div v-bind:class="{hiddenClass: notManager}" style="font-size:15px">
				        <button v-on:click="createVehicle"> Create new vehicle </button>
				    </div>
				    <div v-bind:class="{hiddenClass: notAdmin}" style="margin-top: 20px; font-size:15px">
				        <button v-on:click="createRentACar"> Create new rent a car </button>
				    </div>
			    </div>
			</div>
			<div class="column">
				//ovde planiram liste iznajmljivanja ili ako ne stane onda negde drugde
			</div>
		</div>
	    <div v-if="isCustomer">
	    	<vehiclesTemplate></vehiclesTemplate>
	    </div>
	</div>
	`,
	mounted: function() {
    	axios.get("rest/currentUser")
			.then( response =>
				this.user = response.data
				)
		setTimeout(() => {
        	this.setAppearance()
      	}, 200)
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
		createRentACar: function(){
			event.preventDefault()
			router.push(`/rentacarcreate/`)
		},
		createVehicle: function(){
			router.push(`/vehiclecreate/`)
		},
		setAppearance: function(){
			let date = new Date(this.user.dateOfBirth)
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
			this.user.dateOfBirth = year+'-'+zeroAtMonth+month+'-'+zeroAtDay+day
			console.log(this.user.dateOfBirth)
			if(this.user.role != "administrator"){
				this.notAdmin = true
			}
			if(this.user.role != "manager"){
				this.notManager = true
			}	
			if(this.user.role == "customer"){
				this.isCustomer = true
			}
		}
	}
})