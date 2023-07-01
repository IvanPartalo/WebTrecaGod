Vue.component("vehiclesTemplate",{
	data:function(){
		return{
			vehicles : null,
			purchase : {startDateTime:null, endDateTime:null}
		}
	},
	template: `
	<div>
		<h1 style="text-align: center">Vehicles</h1>
		<h3 style="margin-left:12%">Search cars for specific dates:</h3>
		<div style="margin-left:12%; margin-bottom:15px">
			<label>Start date: </label>
			<input id="startingDT" type="datetime-local" v-model="purchase.startDateTime" style="margin-right:20px">
			<label>End date: </label>
			<input id="endingDT" type="datetime-local" v-model="purchase.endDateTime" style="margin-right:20px">
			<button v-on:click="searchCars()">Search!</button>
		</div>
	    <div v-for="v in vehicles" style="border:1px solid black; font-size:21px; padding: 10px; width: 70%; margin: 0% 12% 1% 12%; background-color: #FBD603">
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
						<div align="right">
							<button v-on:click="addToCart(v.id)">Add to cart!</button>
						</div>
					</div>
				</div>
			</div>
	    </div>
	 </div>
	`,
	computed:{
	},
	mounted: function() {
		setTimeout(() => {
        	this.setAppearance()
      	}, 200)
    },
	methods:{
		setAppearance(){
			//sa stackoverflow-a preuzeta funkcija koja pravi string za datetime-local
			document.getElementById("startingDT").min = new Date().toISOString().slice(0,new Date().toISOString().lastIndexOf(":"))
			document.getElementById("endingDT").min = document.getElementById("startingDT").min
			console.log(document.getElementById("startingDT").min);
		},
		addToCart : function(id){
			axios.post('rest/users/addToCart/'+ id, this.purchase).then(response => axios.post('rest/rentacar/vehicles', this.purchase).then(response => this.vehicles = response.data))
		},
		searchCars : function(){
			if(this.purchase.startDateTime == null || this.purchase.endDateTime == null){
				alert("Select the dates for search!")
				return
			}
			if(this.purchase.startDateTime >= this.purchase.endDateTime){
				alert("Starting datetime has to be before end datetime!")
				return
			}
			axios.post('rest/rentacar/vehicles', this.purchase)
    		.then(response => this.vehicles = response.data)
		}
	}
})