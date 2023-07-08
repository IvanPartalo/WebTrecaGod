Vue.component("createVehicle",{
	data:function(){
		return{
			vehicle: {brand:"", model:"", price:"", type:"", consumption:"",
			 doors:"", maxPeople:"", description:"", fuelType: "diesel", gearshiftType: "manual", photo:""},
			errorMessage: ""
		}
	},
	template: `
	<div >
		<h1 style="width:200px; margin:auto">Create new vehicle</h1>
		<div style="width:480px; float:left; border:1px outset">
		<h3>Image preview</h3>
			<img v-bind:src="vehicle.photo" style="width:400px; height: 400px"/>
		</div>
		<div style="width:480px; margin:auto; font-size:18px">
			<form>
				<div style="border:1px solid black; margin:5px">
					<div style="margin:10px">
						<label>Image*</label>
						<label for="imageInput" class="file-upload-label" style="float:right">Choose image</label>
						<input type="file" id="imageInput" v-on:change="uploadImage" style="float:right; font-size:17px">
					</div>
					<div style="margin:10px; margin-top:15px">
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
						<select v-model="vehicle.fuelType" style="float:right; font-size:18px; width:200px">
							<option>diesel</option>
							<option>benzine</option>
							<option>hybrid</option>
							<option>electric</option>
						</select>
					</div>
					<div style="margin:10px">
						<label>Gearshift type*</label>
						<select v-model="vehicle.gearshiftType" style="float:right; font-size:18px; width:200px">
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
					<input type="submit" value="Create vehicle!" v-on:click="createVehicle" style="background-color:powderblue; font-size:20px">
				</div>
				<br>
				<p v-if="errorMessage.length" style="color:red; width:200px; margin:auto">{{errorMessage}}</p>
			</form>
		</div>
	</div>
	`,
	mounted(){
	},
	methods:{
		uploadImage: function(e) {
	      var files = e.target.files || e.dataTransfer.files;
	      if (!files.length)
	        return;
	      const file = files[0];
	      const fr = new FileReader();
		  fr.readAsDataURL(file);
		  setTimeout(() => {
		  const url = fr.result
		  this.vehicle.photo = url
      	  }, 200)
	      
		},
		createVehicle: function(){
			event.preventDefault()
			this.errorMessage = ""
			if(!this.vehicle.photo){
				this.errorMessage = "You must enter vehicle photo"
				return;
			}
			if(!this.vehicle.brand){
				this.errorMessage = "You must enter brand"
				return;
			}
			if(!this.vehicle.model){
				this.errorMessage = "You must enter model"
				return;
			}
			if(!this.vehicle.price){
				this.errorMessage = "You must enter price"
				return;
			}
			if(!this.vehicle.consumption){
				this.errorMessage = "You must enter consumption"
				return;
			}
			if(!this.vehicle.doors){
				this.errorMessage = "You must enter number of doors"
				return;
			}
			if(!this.vehicle.maxPeople){
				this.errorMessage = "You must enter people capacity"
				return;
			}
			axios.post("rest/rentacar/vehicle", this.vehicle)
			.then(response => ( router.push(`/user/`)))
		}
	}
})