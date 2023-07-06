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
			automatic: false
		}
	},
	template: `
	<div>
		<dialog id="dijalog" ref="dijalog" style="padding: 20px; margin-left:auto; margin-top:200px; margin-right:20px; position:absolute">
			<h4>Filters</h4>
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
			<select v-model="fuelType" style="margin-left:10px">
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
		<label>Name: </label><input type="text" v-model="nameSearch" style="margin-right:20px">
		<label>Vehicle type: </label><input type="text" v-model="vehicleType" style="margin-right:20px">
		<label>Location: </label><input type="text" v-model="locationSearch" style="margin-right:20px">
		<label>Minimum grade: </label><input type="number" v-model="minGrade" style="margin-right:20px">
		<label style="margin-left:20px">Sort</label>
		<select v-model="sorting" style="margin-left:10px">
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
		<button v-on:click="showDialog" style="margin-left:20px">Filters</button>
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
		    	<p>Rating: {{r.grade}}/10</p>
	    	</div>
	    	
	    	<div style="overflow: hidden; padding: 40px;">
				<p>Vehicles in offer:</p>
				<button v-on:click="showRentACar(r.id)">Show</button>
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
			this.rentACarsCopy = this.rentACars.slice()})
    },
	methods:{
		makeCopy: function(){
			this.rentACarsCopy = this.rentACars.slice()
		},
		showDialog: function(){
			this.filterApplied = false
			this.$refs.dijalog.showModal()
		},
		closeDialog: function(){
			this.filterApplied = true
			this.$refs.dijalog.close()
		},
		showRentACar: function(id){
			router.push(`/singleRentACar/${id}`)
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