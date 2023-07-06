Vue.component("rentACarCreate",{
	data:function(){
		return{
			rentACarDTO: {name:"", address:"", longitude:"", latitude:"", beginWorkTime:'08:00', endWorkTime:'16:00'},
			streetAndNumber: '',
			place: '',
			zipCode: '',
			errorMessage: "",
			managers: null,
			selectedManager: null,
			id:-1,
			buttonText: "Create!",
			additionalInfo: "",
			errorMessage: '',
			map: null
		}
	},
	template: `
	<div >
		<h1 style="width:400px; margin:auto">Create new rent a car</h1>
		<div style="width:480px; float:left; border:1px outset"></div>
		<div style="width:480px; font-size:18px; float:left">
			<form>
				<div style="border:1px solid black; margin:5px">
					<div style="margin:10px">
						<label>Name*</label>
						<input type="text" v-model="rentACarDTO.name" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Street and number*</label>
						<input type="text" v-model="streetAndNumber" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Place*</label>
						<input type="text" v-model="place" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Zip code*</label>
						<input type="number" v-model="zipCode" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>longitude*</label>
						<input v-model="rentACarDTO.longitude" type="number" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>latitude*</label>
						<input type="number" v-model="rentACarDTO.latitude" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Begin work time*</label>
						<input type="time" v-model="rentACarDTO.beginWorkTime" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>End work time*</label>
						<input type="time" v-model="rentACarDTO.endWorkTime" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Manager*</label>
						<select style="float:right; font-size:17px" v-model="selectedManager">
						<option v-for="m in managers" :value="m">
						{{m.username}}
						</option>
						</select>
						<br>
					</div>
					
				</div>
				<p v-if="errorMessage.length" style="color:red; width:200px; margin:auto">{{errorMessage}}</p>
				<p>{{additionalInfo}}</p>
				<div style="width:80px;">
					<input type="submit" v-bind:value="buttonText" v-on:click="create" style="background-color:powderblue; font-size:20px;">
				</div>
				<br>
			</form>
		</div>
		<div id="map" class="map" style="float:left; height:400px; width:400px" v-on:click="getCoordinates">
		
		</div>
		<div style="clear:both; ">
		<p style="right:0; position:absolute; padding 10px; padding-right:150px">Click anywhere on map to select longitude and latitude</p>
		</div>
	</div>
	`,
	mounted(){
		axios.get('rest/users/freeManagers').
		then(response => (this.managers = response.data))
		setTimeout(() => {
        	this.setButtonText()
      	}, 200)
      	this.map = new ol.Map({
        target: 'map',
        layers: [
          new ol.layer.Tile({
            source: new ol.source.OSM()
          })
        ],
        view: new ol.View({
          center: ol.proj.fromLonLat([19, 45]),
          zoom: 4
        })
      });

	},
	methods:{
		setButtonText: function(){
			if(this.managers.length == 0)
			{
				this.buttonText = "Create and add new manager"
				this.additionalInfo = "There is no free managers, you will need to add new one."
			}
		},
		getCoordinates: function(evt){
			this.map.on('click', function(evt){
			    test(evt)
			});
        	this.setCoordinates()
		},
		setCoordinates: function(){
			coords = getCoords()
			this.rentACarDTO.longitude = coords[0]
			this.rentACarDTO.latitude = coords[1]
		},
		create: function(){
			event.preventDefault()
			this.rentACarDTO.address = this.streetAndNumber+'@'+this.place+' '+this.zipCode
			
			if(this.rentACarDTO.name.trim().length == 0){
				this.errorMessage = "Enter name!"
				return;
			}
			else if(this.streetAndNumber.trim().length == 0){
				this.errorMessage = "Enter street and number!"
				return;
			}
			else if(this.place.trim().length == 0){
				this.errorMessage = "Enter place!"
				return;
			}
			else if(this.zipCode.trim().length == 0){
				this.errorMessage = "Enter zip code!"
				return;
			}
			else if(!this.rentACarDTO.longitude){
				this.errorMessage = "Enter longitude!"
				return;
			}
			else if(!this.rentACarDTO.latitude){
				this.errorMessage = "Enter latitude!"
				return;
			}
			else if(this.rentACarDTO.beginWorkTime == null){
				this.errorMessage = "Enter begin work time!"
				return;
			}
			else if(this.rentACarDTO.endWorkTime == null){
				this.errorMessage = "Enter end work time!"
				return;
			}
			
			if(this.managers.length != 0)
			{
				this.id = this.selectedManager.id
				axios.post("rest/rentacar/"+this.id, this.rentACarDTO)
				.then(response => ( router.push(`/user/`)))
			}
			else
			{
				axios.post("rest/rentacar/", this.rentACarDTO)
				.then(response => ( router.push(`/managercreate/`)))
			}
		}
	}
})