Vue.component("profilePage",{
	data:function(){
		return{
			notAdmin: false,
			notManager: false,
			isCustomer: false,
			isManager: false,
			isAdmin: false,
			id: null,
			user: {id: null, username: null, password: null, firstName: null, 
			lastName: null, gender: null, role: null, dateOfBirth: null, collectedPoints:null, 
			customerType:{name:null, discount:null, requiredPoints:null}},
			rentACar: null,
			comments: null
		}
	},
	template: `
	<div>
		<div style="background-color:powderblue; font-size:20px">
			<a href="" v-on:click="showRentACar" style="margin-left:15px">Rent a car shops</a>
			<a href="" v-on:click="edit" style="margin-left:15px">Edit profile</a>
			<a href="" v-on:click="changePassword" style="margin-left:15px">Change password</a>
			<a v-if="user.role == 'customer'" href="" v-on:click="goToCart" style="margin-left:15px">Cart</a>
			<a href="" v-on:click="logOut" style="float:right; margin-right:30px">Log out</a>
		</div>
		<div class="row">
			<div class="column">
			    <div style="border:1px solid black; padding-left: 5px;border-radius: 30px; margin:1%;text-align: center;background-color:#8083c9">
					<h1 style="color:red;">Personal info</h1>
			        <h2>{{user.firstName}} {{user.lastName}}</h2>
				    <div style="padding-left: 5px; font-size:19px">
				        <label>Username:</label>
				        <b><label>{{user.username}}</label><br><br></b>
				    </div>
				    <div style="padding-left: 5px; font-size:19px">
				        <label>Gender:</label>
				        <b><label>{{user.gender}}</label><br><br></b>
				        <label>Date of birth:</label>
				        <b><label>{{user.dateOfBirth}}</label><br><br></b>
				        <label>Role:</label>
				        <b><label>{{user.role}}</label><br><br></b>
				    </div>
				     <div v-if="isCustomer" style="padding-left: 5px; font-size:19px">
				    	<label>Collected points:</label>
				    	<b><label>{{user.collectedPoints}}</label><br><br></b>
				    	<label>Customer type:</label>
				    	<b><label>{{user.customerType.name}}</label><br><br></b>
				    </div>
				    <div v-bind:class="{hiddenClass: notManager}" style="font-size:19px">
				        <button v-on:click="createVehicle"> Create new vehicle </button>
				    </div>
				    <div v-bind:class="{hiddenClass: notAdmin}" style="margin-bottom: 5px; font-size:19px">
				        <button v-on:click="createRentACar"> Create new rent a car </button>
				    </div>
			    </div>
			    <div v-if="user.role == 'customer'">
			    	<vehiclesTemplate></vehiclesTemplate>
			    </div>
			</div>
			<div class="column">
				 <div v-if="user.role == 'customer'">
			    	<customersRentings @changed="changedCancel"></customersRentings>
			    </div>
			    <div v-if="user.role == 'manager'">
			    	<managersRentings></managersRentings>
			    </div>
			</div>
		</div>
		<div v-if="isAdmin">
			<customersTemplate></customersTemplate>
		</div>
		<div v-if="isManager">
		<div style="margin:10px">
			<h1 style="font-size:40px">{{rentACar.name}}</h1>
			<img v-bind:src="rentACar.logoImg" style="width:100px; height:100px" />
		</div>
		<div style="float:left; margin:10px; font-size:20px">
			
			<div style="float:left; margin:10px">
				<b>Location:</b>
			</div>
			<div style="float:left; margin:10px; margin-left:10px">
		        	{{rentACar.location.streetNumber}} <br>
		        	{{rentACar.location.placeZipCode}}<br> 
		        	{{rentACar.location.longitude}}, {{rentACar.location.latitude}}
	        </div>
	        <div style="float:left; margin:10px">
	        	<b>Working time:</b>
	        </div>
	        <div style="float: left; margin:10px">
	        	{{rentACar.startHour}}:{{rentACar.startMinute}} - {{rentACar.endHour}}:{{rentACar.endMinute}}
	        	<br>
	        	{{rentACar.status}}
			</div>
	        <div style="float:left; margin:10px">
	        	<b>Rating:</b>
	        </div>
	        <div style="float: left; margin:10px; margin-left:10px">
	        	{{rentACar.grade}}/10
			</div>
		</div>
		<br><br><br><br><br><br>
			<div style="clear:both">
			<div style="margin: 50px; margin-left: 120px">
				<h2 style="text-align:left"> Vehicles </h2>
				<div v-for="v in rentACar.vehicles" style="border:1px solid black; font-size:21px; padding: 10px; width: 70%; margin: 0% 12% 1% 12%; background-color: #FBD603">
	    	<div>
				<div class="row">
					<div class="column">
						<div class="image">
							<img v-bind:src="v.photo" style="width:140px; height:100px" />
						</div>
						<div>
							<label>Model:</label>
							<b><label>{{v.model}}</label></b>
						</div>
					</div>
					<div class="column">
						<label>Brand:</label>
						<b><label>{{v.brand}}</label></b><br>
						<label>Price:</label>
						<b><label>{{v.price}}</label></b><br>
						<label>Type:</label>
						<b><label>{{v.type}}</label></b><br>
						<label>Fuel type:</label>
						<b><label>{{v.fuelType}}</label></b><br>
					</div>
					<div class="column">
						<label>Consumption:</label>
						<b><label>{{v.consumption}}</label></b><br>
						<label>Number of doors:</label>
						<b><label>{{v.doors}}</label></b><br>
						<label>Max people:</label>
						<b><label>{{v.maxPeople}}</label></b><br>
						<label>Desription:</label>
						<b><label>{{v.description}}</label></b><br><br>
						<button v-on:click="editVehicle(v.id)" style="height:30px; width:120px">Change</button>
						<br>
						<button v-on:click="deleteVehicle(v.id)" style="height:30px; margin-top:10px; width:120px">Delete</button>
					</div>
					
				</div>
			</div>
	    </div>	
			</div>
			</div>
			<div style="margin: 50px; margin-left: 120px">
				<h2 style="text-align:left"> Comments </h2>
				<div style="margin:20px;">
					<div v-for="c in comments" style="display:flex">
					<div style="border-style: outset; flex:1">
						<h3>{{c.customer.username}}</h3>
						<p style="font-size:18px; padding-left:20px">{{c.commentText}}</p>
						<p style="font-size:18px; padding-left:20px">Grade: {{c.grade}}/10</p>
					</div>
					<div style="text-align:center">
						<p>Status:</p>
						<p v-if="c.approved" style="margin-left:20px"><b>Approved</b></p>
						<p v-if="!c.approved"><b>Not approved</b></p>
						<button v-if="!c.approved" v-on:click="ApproveComment(c)">Approve</button>		
					</div>
					</div>
				</div>
			</div>
			
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
		changedCancel: function(){
			axios.get("rest/currentUser")
			.then( response =>
				this.user = response.data
				)
		setTimeout(() => {
        	this.setAppearance()
      	}, 200)
		},
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
		showRentACar: function(){
			event.preventDefault()
			router.push(`/rentacar/`)
		},
		goToCart: function(){
			event.preventDefault()
			router.push(`/cart`)
		},
		createRentACar: function(){
			event.preventDefault()
			router.push(`/rentacarcreate/`)
		},
		createVehicle: function(){
			router.push(`/vehiclecreate/`)
		},
		editVehicle: function(id){
			router.push(`/editvehicle/${id}`)
		},
		deleteVehicle: function(id){
			let conf = confirm("Are you sure? Click ok to confirm.")
			if(conf){
				axios.delete('rest/vehicles/'+id)
				.then(response => this.setAppearance())
			}
		},
		ApproveComment: function(c){
			c.approved = true
			axios.put('rest/rentacar/commentapproval/'+c.id)
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
			if(this.user.role == "administrator"){
				this.isAdmin = true
			}
			if(this.user.role == "manager"){
				axios.get('rest/rentacar/manager/'+this.user.id)
    			.then(response => this.rentACar = response.data)
				setTimeout(() => {
				this.isManager = true
				axios.get('rest/rentacar/allcomments/'+this.rentACar.id)
      			.then(response => this.comments = response.data)
      			}, 200)
			}
		}
	}
})