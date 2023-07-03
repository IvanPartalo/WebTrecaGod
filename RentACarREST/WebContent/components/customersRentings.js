Vue.component("customersRentings",{
	data:function(){
		return{
			rentings : null
		}
	},
	template: `
	<div style="overflow-y:scroll; height:800px;">
		<h3 style="text-align: center">Your rentings</h3>
		<div v-for="(r, index) in this.rentings" style="border:1px solid black; background-color: #CBC3E3; margin: 0% 0% 1% 0%;">
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
			    </div>
		    </div>
		</div>
	 </div>
	`,
	computed:{
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
		}
	}
})