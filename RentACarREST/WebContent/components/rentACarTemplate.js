Vue.component("rentACarTemplate",{
	data:function(){
		return{
			rentACars: null,
			rentACarsCopy: null,
			nameSearch: '',
			locationSearch: '',
			vehicleType: '',
			minGrade: 0,
			sorting: 'None',
			sortingType: 'ascending',
			ascending: 1,
			workFilter: false,
			fuelType: 'all',
			filterApplied: false,
			manual: false,
			automatic: false,
			admin: null,
			isAdmin: false,
		}
	},
	template: `
	<div>
		<dialog id="dijalog" ref="dijalog" style="padding: 20px; margin-left:auto; margin-top:200px; margin-right:20px; position:absolute">
			<h4 style="margin-top:-10px;">Filters</h4>
			<button v-on:click="CloseAndReset" style="position:absolute; top:0; right:0; background-color:#e1e8e8"><b>X</b></button>
			<input type="checkbox" value="working" v-model="workFilter">
			<label>Show working only</label>
			<br>
			<input type="checkbox" value="manual" v-model="manual">
			<label>Show manual only</label>
			<br>
			<input type="checkbox" value="automatic" v-model="automatic">
			<label>Show automatic only</label>
			<br><br>
			<label>Fuel type</label>
			<select class="searchInput" v-model="fuelType" style="margin-left:10px; border:1px solid">
				<option>all</option>
				<option>diesel</option>
				<option>benzine</option>
				<option>electric</option>
				<option>hybrid</option>
			</select>
			<br><br>
			<button v-on:click="closeDialog">Apply</button>
		</dialog>
		<h1 style="text-align: center">Rent a car shops</h1>
		
		<h3 style="margin-left:20px">Search for specific shop</h3>
		<div style="margin-left: 40px">
		<div style="float:left">
		<label>Name: </label><input class="searchInput" type="text" v-model="nameSearch" style="margin-right:20px">
		<label>Vehicle type: </label><input class="searchInput" type="text" v-model="vehicleType" style="margin-right:20px">
		<label>Location: </label><input class="searchInput" type="text" v-model="locationSearch" style="margin-right:20px">
		<label>Minimum grade: </label><input class="searchInput" type="number" v-model="minGrade" style="margin-right:20px">
		<label style="margin-left:20px">Sort</label>
		<select class="searchInput" v-model="sorting" style="margin-left:10px">
			<option>None</option>
			<option>Grade</option>
			<option>Name</option>
			<option>Location</option>
		</select>
		</div>
		<div style="float:left; margin-left: 10px">
		<div>
		<input type="radio" id="asc" name="sort_type" value="ascending" v-model="sortingType">	
		<label for="asc">ascending</label>
		</div>
		<div>
		<input type="radio" id="desc" name="sort_type" value="descending" v-model="sortingType">
		<label for="desc">descending</label>
		</div>
		</div>
		<div style="float:left">
		<button class="filterButton" v-on:click="showDialog" style="margin-left:20px">
			<img src="https://cdn-icons-png.flaticon.com/512/107/107799.png" style="width:10px; height:10px"/>
			Filters
		</button>
		</div>
		</div>
	    <div v-for="r in rentACarList" style="border:1px solid black; font-size:20px; overflow: hidden; padding: 15px; width: 70%; margin: 0% 12% 1% 12%;background-color: #FBD603">
	    	<div style="float: left">
				<div class="container">
					<div class="image">
						<img v-bind:src="r.logoImg" style="width:50px; height:50px" />
					</div>
					<div class="text">
						<h1>{{r.name}}</h1>
					</div>
				</div>
				<div style="float: left">
					Location:
				</div>
				<div style="float: left; margin-left:10px">
			        <p>{{r.location.streetNumber}} <br>{{r.location.placeZipCode}}<br> {{r.location.longitude}}, {{r.location.latitude}}</p>
		        </div>
		    	<p><br><br><br><br>Working time: {{r.startHour}}:{{r.startMinute}} - {{r.endHour}}:{{r.endMinute}} {{r.status}}</p>
		    	<p v-if="r.grade == 0">Not graded yet</p>
		    	<p v-else>Rating: {{r.grade}}/5</p>
	    	</div>
	    	
	    	<div style="overflow: hidden; margin-top:40px; padding: 40px;">
				<button v-on:click="showRentACar(r.id)" style="font-size:20px; border-radius:10px; padding:10px; 
				background-color:#6cacf5">
					More details
				</button>
	    	</div>
	    	<div v-if="isAdmin">
	    		<button v-on:click="deleteRentACar(r.id)"
	    		 style="font-size:18px; border-radius:10px; padding:10px; 
				background-color:#6cacf5; margin-left:30px"> 
					<img src="https://cdn-icons-png.flaticon.com/512/535/535246.png" style="width:15px; height:15px"/>
					Delete this shop
				</button>
	    	</div>
	    </div>
	 </div>
	`,
	computed:{
		rentACarList(){
			let filteredList = this.rentACars
			
			if(this.sortingType == 'ascending'){
				this.ascending = 1
			}
			else{
				this.ascending = -1
			}
			if (this.sorting == "None"){
				filteredList = this.rentACarsCopy
			}
			if(this.nameSearch.length > 0 || this.locationSearch.length > 0 || this.minGrade.length > 0 || this.vehicleType.length > 0 || this.sorting != 'None' || this.filterApplied){
				if(this.filterApplied)
				{
					if(this.workFilter){
						filteredList = filteredList.filter((rentACar) => rentACar.status == "working")
					}
					if (this.fuelType != "all"){
						filteredList = this.getByFuelType(filteredList)
					}
					if (this.manual){
						filteredList = this.getByGearType(filteredList, 'manual')
					}
					if (this.automatic){
						filteredList = this.getByGearType(filteredList, 'automatic')
					}
				}
				if (this.sorting == "Grade"){
				filteredList.sort((a, b) => {
				  const gradeA = a.grade;
				  const gradeB = b.grade;
				  return this.moveElements(gradeA, gradeB)
				});
				}
				if (this.sorting == "Name"){
					filteredList.sort((a, b) => {
					  const A = a.name;
					  const B = b.name;
					  return this.moveElements(A, B)  
					});
				}
				if (this.sorting == "Location"){
					filteredList.sort((a, b) => {
					  const A = a.location.address;
					  const B = b.location.address;
					  return this.moveElements(A, B)
					});
				}
				if (this.nameSearch.length > 0){
					filteredList = filteredList.filter((rentACar) => rentACar.name.toLowerCase().includes(
					this.nameSearch.toLowerCase()))
				}
				if (this.vehicleType.length > 0){
					filteredList = this.getByVehicleType(filteredList)
				}
				if (this.locationSearch.length > 0){
					filteredList = filteredList.filter((rentACar) => rentACar.location.address.toLowerCase().includes(
					this.locationSearch.toLowerCase()))
				}
				if (this.minGrade.length > 0){
					filteredList = filteredList.filter((rentACar) => rentACar.grade >= this.minGrade)
				}
				
				return filteredList
			}
			return this.rentACarsCopy
		}
	},
	mounted: function() {
    	axios.get('rest/rentacar/')
    	.then(response => {
			this.rentACars = response.data
			this.rentACarsCopy = this.rentACars.slice()
			this.rentACars.forEach((rentACar) => {
				rentACar.location.longitude = rentACar.location.longitude.toFixed(2)
				rentACar.location.latitude = rentACar.location.latitude.toFixed(2)
				if(rentACar.startHour < 10){
					rentACar.startHour = '0'+rentACar.startHour
				}
				if(rentACar.startMinute < 10){
					rentACar.startMinute = '0'+rentACar.startMinute
				}
				if(rentACar.endHour < 10){
					rentACar.endHour = '0'+rentACar.endHour
				 }
				if(rentACar.endMinute < 10){
					rentACar.endMinute = '0'+rentACar.endMinute
				}
			});
			this.rentACarsCopy = this.rentACarsCopy.sort((a, b) => {
				  const A = a.status;
				  const B = b.status;
				  return this.moveElements(B, A)
				});
			})
		axios.get('rest/currentUser')
		.then(response => {
			this.admin = response.data
			if(this.admin != null){
				if(this.admin.role == 'administrator'){
					this.isAdmin = true
				}
			}
		})
			
    },
	methods:{
		showDialog: function(){
			this.filterApplied = false
			this.$refs.dijalog.showModal()
		},
		closeDialog: function(){
			this.filterApplied = true
			this.$refs.dijalog.close()
		},
		CloseAndReset: function(){
			this.filterApplied = true
			this.fuelType = 'all'
			this.manual = false
			this.automatic = false
			this.workFilter = false
			this.$refs.dijalog.close()
		},
		showRentACar: function(id){
			router.push(`/singleRentACar/${id}`)
		},
		deleteRentACar: function(id){
			let conf = confirm("Are you sure? Click ok to confirm.")
			if(conf){
				axios.delete('rest/rentacar/deleterent/'+id)
				.then(response => (router.push(`/rentacar/`)))
				axios.get('rest/rentacar/')
				.then(response => {
				this.rentACars = response.data
				this.rentACarsCopy = this.rentACars.slice()
				this.rentACars.forEach((rentACar) => {
					rentACar.location.longitude = rentACar.location.longitude.toFixed(2)
					rentACar.location.latitude = rentACar.location.latitude.toFixed(2)
					if(rentACar.startHour < 10){
						rentACar.startHour = '0'+rentACar.startHour
					}
					if(rentACar.startMinute < 10){
						rentACar.startMinute = '0'+rentACar.startMinute
					}
					if(rentACar.endHour < 10){
						rentACar.endHour = '0'+rentACar.endHour
					 }
					if(rentACar.endMinute < 10){
						rentACar.endMinute = '0'+rentACar.endMinute
					}
				});
				})
			}
		},
		getByGearType: function(rentList, type){
			let add = true
			let newList = []
			rentList.forEach((rentACar) => {
				add = false
				rentACar.vehicles.forEach((vehicle) => {
					if(vehicle.gearshiftType == type){
						add = true		
					}							
				});
				if(add){
					newList.push(rentACar)
				}
			});
			return newList
		},
		getByFuelType: function(rentList){
			let add = true
			let newList = []
			rentList.forEach((rentACar) => {
				add = false
				rentACar.vehicles.forEach((vehicle) => {
					if(vehicle.fuelType == this.fuelType){
						add = true		
					}							
				});
				if(add){
					newList.push(rentACar)
				}
			});
			return newList
		},
		getByVehicleType: function(rentList){
			let add = true
			let newList = []
			rentList.forEach((rentACar) => {
				add = false
				rentACar.vehicles.forEach((vehicle) => {
					if(vehicle.type.toLowerCase().includes(this.vehicleType)){
						add = true		
					}							
				});
				if(add){
					newList.push(rentACar)
				}
			});
			return newList
		},
		moveElements: function(a, b){
			if (a < b) {
			    return -1*this.ascending;
			}
			if (a > b) {
				return 1*this.ascending;
			}
			return 0;
		}
	}
})