Vue.component("managersRentings",{
	data:function(){
		return{
			rentings:null,
			manager:null
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
									<div style="background-color: #ADFF2F" v-if="v.fromCurrentRentACar == true">
										<label>YOUR CAR</label>
									</div>
									<div class="image">
										<br><img v-bind:src="v.photo" style="width:110px; height:75px" />
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
									<b><label >{{v.price}}</label></b><br>
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
			    	<div v-for="sp in r.subPurchases">
			    		<div v-if="sp.fromCurrentRentACar == true">
			    			<div v-if="sp.status == 'pending'">
			    				<div v-if="r.status == 'pending'">
						    		<br><br><button style="font-size:19px" v-on:click="accept(r, index)">Accept!</button><br><br><br><br><br>
							    	<button style="font-size:19px" v-on:click="decline(r, index)">Decline!</button><br><br>
							    	<label>Leave a comment</label><br>
							    	<input v-model="r.decliningReason" type="text">
						    	</div>
						    	
			    			</div>
			    			<div v-else>
			    				<div v-if="sp.status == 'accepted'">
				    				<div v-if="r.status == 'accepted'">
							    		<br><br><br><button style="font-size:19px" v-on:click="take(r, index)">Set your cars as taken!</button><br><br><br>
							    	</div>
				    			</div>
			    				<div v-if="sp.status == 'taken'">
				    				<div v-if="r.status == 'accepted'">
							    		<br><br><label style="font-size:20px">Your cars are already taken as part of this renting!</label>
							    	</div>
							    	<div v-if="r.status == 'taken'">
							    		<br><br><button style="font-size:19px" v-on:click="returnCars(r, index)">Set as returned!</button><br><br><br><br><br>
							    	</div>
				    			</div>
				    			<div v-if="sp.status == 'returned'">
				    				<div v-if="r.status == 'taken'">
							    		<br><br><label style="font-size:20px">Your cars are already returned as part of this renting!</label>
							    	</div>
				    			</div>
			    				<div v-if="r.status == 'pending'">
					    			<br><br><label style="font-size:20px">You already accepted your part of this renting!</label>
					    		</div>
					    	</div>
				    	</div>
			    	</div>
			    	<div v-if="r.status == 'declined'">
			    		<br><label>Reason for declining:</label><br>
			    		<br><label style="font-size:19px">{{r.decliningReason}}</label><br><br><br>
			    	</div>
			    </div>
		    </div>
		</div>
	 </div>
	`,
	computed:{
	},
	mounted: function() {
			axios.get("rest/currentUser")
			.then( response =>
				this.manager = response.data
				),
        	axios.get("rest/users/managersRentings")
			.then( response =>
				this.rentings = response.data
				)
    },
	methods:{
		accept : function(r, index){
			axios.put('rest/users/accept/' + r.id + "/" + this.manager.rentACarId).then(response => 
			axios.get("rest/users/managersRentings")
			.then( response =>
				this.rentings = response.data
				)
			)
		},
		decline : function(r, index){
			if(r.decliningReason.trim().length == 0){
				alert("You have to give a comment when declining!")
				return;
			}
			axios.put('rest/users/decline/' + r.id + "/" + r.decliningReason).then(response => 
			axios.get("rest/users/managersRentings")
			.then( response =>
				this.rentings = response.data
				)
			)
		},
		take : function(r, index){
			if(true){
				let currentDate = new Date()
				if(r.start.year == currentDate.getFullYear()){
					if(r.start.monthValue == (1+currentDate.getMonth())){
						if(r.start.dayOfMonth <= currentDate.getDate()){
							axios.put('rest/users/take/' + r.id + "/" + this.manager.rentACarId).then(response => 
							axios.get("rest/users/managersRentings")
							.then( response =>
								this.rentings = response.data
								)
							)
							return
						}
					}else if(r.start.monthValue < (1+currentDate.getMonth())){
						axios.put('rest/users/take/' + r.id + "/" + this.manager.rentACarId).then(response => 
						axios.get("rest/users/managersRentings")
						.then( response =>
							this.rentings = response.data
							)
						)
						return
					} 
				}else if(r.start.year < currentDate.getFullYear()){
					axios.put('rest/users/take/' + r.id + "/" + this.manager.rentACarId).then(response => 
					axios.get("rest/users/managersRentings")
					.then( response =>
						this.rentings = response.data
						)
					)
					return
				}
				alert("Cars can be taken on the first day of the purchase at the earliest!")
				return;
			}
		},
		returnCars : function(r, index){
			axios.put('rest/users/return/' + r.id + "/" + this.manager.rentACarId).then(response => 
			axios.get("rest/users/managersRentings")
			.then( response =>
				this.rentings = response.data
				)
			)
		}
	}
})