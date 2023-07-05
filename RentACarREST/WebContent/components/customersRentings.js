Vue.component("customersRentings",{
	data:function(){
		return{
			rentings : null,
			beginningDate: "",
			endingDate: "",
			minPrice: null,
			maxPrice: null,
			rentACarObject: '',
			sortingType: 'ascending',
			sort: 'None',
			ascending: 1
		}
	},
	template: `
	<div style="overflow-y:scroll; height:800px;">
		<h3 style="text-align: center">Your rentings</h3>
		<h4> Search rentings: </h4>
		<table cellspacing="15" style="margin-top:-20px">
			<tr style="text-align: center">
				<td>Name of rent a car</td>
				<td>Min price</td>
				<td>Max price</td>
				<td>Beginning date</td>
				<td>Ending date</td>
			</tr>
			<tr>
				<td><input type="text" style="width: 125px" v-model="rentACarObject"/></td>
				<td><input type="number" style="width: 100px" v-model="minPrice"/></td>
				<td><input type="number" style="width: 100px" v-model="maxPrice"/></td>
				<td><input type="date" v-model="beginningDate"/></td>
				<td><input type="date" v-model="endingDate"/></td>
			</tr>
		</table>
		<div style="float:left">
			<label style="font-size:16px"><b>Sort:</b></label>
			<select style="margin-left:10px" v-model="sort">
				<option>None</option>
				<option>Rent a car name</option>
				<option>Price</option>
				<option>Date</option>
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
		<br><br><br>
		<div v-for="(r, index) in rentingsList" style="border:1px solid black; background-color: #CBC3E3; margin: 0% 0% 1% 0%;">
			<label>Code: </label>
		    <b><label>{{r.id}}</label></b>
		    <label style="margin-left:10px">Date/time: </label>
	    	<b><label>{{r.start.dayOfMonth}}-{{r.start.month}}-{{r.start.year}} {{r.start.hour}}:{{r.start.minute}}</label></b>
		    <label style="margin-left:10px">Duration: </label>
		    <b><label>{{r.duration}} h</label></b>
		    <label style="margin-left:10px">Price: </label>
		    <b><label>{{r.price}} euros</label></b>
		    <label style="margin-left:10px">Status: </label>
		    <b><label>{{r.status}}</label></b>
		    <div class="row">
			    <div class="columnSpecial1">
				    <div v-for="v in r.vehicles" style="border:1px solid black; font-size:15px; padding: 5px; width: 100%; margin: 0% 0% 1% 1%; background-color: #FBD603">
				    	<div>
							<div class="row">
								<div class="column">
									<div class="image">
										<img v-bind:src="v.photo" style="width:110px; height:75px" />
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
									<label>Gearshift type:</label>
									<b><label>{{v.gearshiftType}}</label></b><br>
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
								</div>
							</div>
						</div>
				    </div>
			    </div>
			    <div class="columnSpecial2" align="center">
			    	<button v-if="r.status == 'pending'" style="font-size:19px" v-on:click="cancel(r, index)">Cancel!</button>
			    	<div v-if="r.status == 'declined'">
			    		<br><label>Reason for declining:</label><br>
			    		<br><label style="font-size:19px">{{r.decliningReason}}</label><br><br><br>
			    	</div>
			    	<div v-if="r.status == 'returned'">
			    		<div v-for="sp in r.subPurchases">
			    			<div v-if="sp.graded == false">
			    				<br><label>Leave a comment</label><br>
							    <input v-model="sp.comment.commentText" type="text">
			    				<label for="grades">Grade</label>
			    				<select v-model="sp.comment.grade" name="grades">
								  <option value="1">1</option>
								  <option value="2">2</option>
								  <option value="3">3</option>
								  <option value="4">4</option>
								  <option value="5">5</option>
								  <option value="6">6</option>
								  <option value="7">7</option>
								  <option value="8">8</option>
								  <option value="9">9</option>
								  <option value="10">10</option>
								</select>
			    				<button v-on:click="grade(r,sp)">Grade rent a car named: {{sp.rentACar.name}}!</button><br><br>
			    			</div>
			    		</div>
			    	</div>
			    </div>
		    </div>
		</div>
	 </div>
	`,
	computed:{
		rentingsList(){
			let filteredList = this.rentings
			
			//sortiranje
			if(this.sortingType == 'ascending'){
				this.ascending = 1
			}
			else{
				this.ascending = -1
			}
			if(this.sort != 'None'){
				if (this.sort == "Price"){
					filteredList.sort((a, b) => {
					  const priceA = a.price;
					  const priceB = b.price;
					  return this.moveElements(priceA, priceB)
					});
				}
				if (this.sort == "Date"){
					filteredList.sort((a, b) => {
					  const A = a.start;
					  const B = b.start;
					  return this.sortDates(A, B)  
					});
				}
				if (this.sort == "Rent a car name"){
					filteredList.sort((a, b) => {
					  const objectA = a.subPurchases[0].rentACar.name;
					  const objectB = b.subPurchases[0].rentACar.name;
					  return this.moveElements(objectA, objectB)
					});
				}
			}
			
			//pretraga
			if(this.beginningDate != "" || this.endingDate != "" || this.minPrice || this.maxPrice || this.rentACarObject){
				if(this.beginningDate != ""){
					let date = new Date(this.beginningDate)
					filteredList = filteredList.filter((renting) => this.isAfterSearchedDate(date, renting.start))
				}
				if(this.endingDate != ""){
					let date = new Date(this.endingDate)
					filteredList = filteredList.filter((renting) => this.isBeforeSearchedDate(date, renting.end))
				}
				if(this.minPrice){
					filteredList = filteredList.filter((renting) => renting.price >= this.minPrice)
				}
				if(this.maxPrice){
					filteredList = filteredList.filter((renting) => renting.price <= this.maxPrice)
				}
				if(this.rentACarObject){
					filteredList = this.getForSearchedRentACars(filteredList)
				}
				return filteredList
			}else{
				return this.rentings
			}
		}
	},
	mounted: function() {
		axios.get("rest/users/customersRentings")
			.then( response =>
				this.rentings = response.data
				)
    },
	methods:{
		cancel : function(r, index){
			axios.put('rest/users/cancel/' + r.id).then(response => 
			//this.rentings[index].status = "aa",
			axios.get("rest/users/customersRentings")
			.then( response =>
				this.rentings = response.data
				)
			)
		},
		grade : function(r, sp){
			axios.put('rest/users/grade/' + r.id, sp).then(response => 
			axios.get("rest/users/customersRentings")
			.then( response =>
				this.rentings = response.data
				)
			)
		},
		isAfterSearchedDate(searchedDate, startDate){
			var day = startDate.dayOfMonth;
			var month = startDate.monthValue - 1;// ovde minus jedan, verovatno kad se pravi datum u js-u indeksira se od 0
			var year = startDate.year;
			var date = new Date(Date.UTC(year, month, day));
			return date >= searchedDate
		},
		isBeforeSearchedDate(searchedDate, endDate){
			var day = endDate.dayOfMonth;
			var month = endDate.monthValue - 1;
			var year = endDate.year;
			var date = new Date(Date.UTC(year, month, day));
			return date <= searchedDate
		},
		getForSearchedRentACars: function(rentings){
			let add = true
			let newList = []
			rentings.forEach((rent) => {
				add = false
				rent.subPurchases.forEach((subPurchase) => {
					if(subPurchase.rentACar.name.toLowerCase().includes(this.rentACarObject.toLowerCase())){
						add = true		
					}							
				});
				if(add){
					newList.push(rent)
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
		},
		sortDates: function(a, b){
			let day = a.dayOfMonth;
			let month = a.monthValue - 1;
			let year = a.year;
			let dateA = new Date(Date.UTC(year, month, day));
			day = b.dayOfMonth;
			month = b.monthValue - 1;
			year = b.year;
			let dateB = new Date(Date.UTC(year, month, day));
			
			if (dateA < dateB) {
			    return -1*this.ascending;
			}
			if (dateA > dateB) {
				return 1*this.ascending;
			}
			return 0;
		}
	}
})