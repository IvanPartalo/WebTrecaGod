Vue.component("editVehicle",{
	data:function(){
		return{
			vehicle: null,
			errorMessage: "",
			id: null
		}
	},
	template: `
	<div >
		<h1 style="width:200px; margin:auto">Edit vehicle</h1>
		<div style="width:480px; margin:auto; font-size:18px">
			<form>
				<div style="border:1px solid black; margin:5px">
					<div style="margin:10px">
						<label>Brand*</label>
						<input type="text" v-model="vehicle.brand" placeholder="Enter brand" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Model*</label>
						<input type="text" v-model="vehicle.model" placeholder="Enter model" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Price*</label>
						<input v-model="vehicle.price" type="number" placeholder="Enter price" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Type*</label>
						<input type="text" v-model="vehicle.type" placeholder="Enter vehicle type" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Consumption*</label>
						<input type="number" v-model="vehicle.consumption" placeholder="Enter consumption" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Doors*</label>
						<input type="number" v-model="vehicle.doors" placeholder="Enter door number" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Capacity*</label>
						<input type="number" v-model="vehicle.maxPeople" placeholder="Enter maximum number of people" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Fuel type*</label>
						<select v-model="vehicle.fuelType">
							<option>diesel</option>
							<option>benzine</option>
							<option>hybrid</option>
							<option>electric</option>
						</select>
					</div>
					<div style="margin:10px">
						<label>Gearshift type*</label>
						<select v-model="vehicle.gearshiftType">
							<option>manual</option>
							<option>automatic</option>
						</select>
					</div>
					<div style="margin:10px">
						<label>Description</label>
						<input type="text" v-model="vehicle.description" placeholder="Description" style="float:right; font-size:17px"><br>
					</div>
				</div>
				<div style="width:80px; margin:auto">
					<input type="submit" value="Edit vehicle!" v-on:click="editVehicle" style="background-color:powderblue; font-size:20px">
				</div>
				<br>
				<p v-if="errorMessage.length" style="color:red; width:200px; margin:auto">{{errorMessage}}</p>
			</form>
		</div>
	</div>
	`,
	mounted(){
		this.id = this.$route.params.id
		axios.get('rest/vehicles/'+this.id)
		.then(response => this.vehicle = response.data)
	},
	methods:{
		editVehicle: function(){
			event.preventDefault()
			this.errorMessage = ""
			this.vehicle.rentACar = null
			//axios.post("rest/rentacar/vehicle", this.vehicle)
			axios.put("rest/vehicles/", this.vehicle)
			.then(response => (router.push(`/user/`)))
		}
	}
})