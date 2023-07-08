Vue.component("singleRentACarTemplate",{
	data:function(){
		return{
			rentACar: null,
			comments: null,
			id: -1,
			map: null,
			commentsEmpty: true,
			vehiclesEmpty: true
		}
	},
	template: `
	<div v-if="rentACar!=null">
	<div style="float:left; margin:10px">
		<img v-bind:src="rentACar.logoImg" style="width:100px; height:100px" />
	</div>
		<div style="float:left; margin:10px; font-size:20px">
			<h1 style="font-size:40px">{{rentACar.name}}</h1>
			<div style="float:left; margin:10px">
				Location:
			</div>
			<div style="float:left; margin:10px; margin-left:50px">
		        	{{rentACar.location.streetNumber}} <br>
		        	{{rentACar.location.placeZipCode}}<br> 
		        	{{rentACar.location.longitude}}, {{rentACar.location.latitude}}
	        </div>
	        <div>
	        <div style="float:left; margin:10px">
	        	Working time:
	        </div>
	        <div style="float: left; margin:10px">
	        	{{rentACar.startHour}}:{{rentACar.startMinute}} - {{rentACar.endHour}}:{{rentACar.endMinute}}
	        	<br>
	        	{{rentACar.status}}
			</div>
			</div>
			<div>
	        <div style="float:left; margin:10px">
	        	Rating:
	        </div>
	        <div style="float: left; margin:10px; margin-left:5px">
	        	{{rentACar.grade}}/10
			</div>
			</div>
		</div>
		<div id="map" class="map" style ="width: 400px; height: 400px; margin:auto;">
			
		</div>
		<br><br>
			<div style="clear:both">
			</div>
			<div style="margin: 50px; margin-left: 120px">
				<h2 style="text-align:left"> Vehicles </h2>
				<div v-if="vehiclesEmpty">
					There are no vehicles at the moment
				</div>
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
						<label>Price per h:</label>
						<b><label>{{v.price}}e</label></b><br>
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
					</div>
				</div>
			</div>
	    </div>
	    <div style="margin-top: 50px; margin-bottom:30px">
			<h2 style="text-align:left"> Comments </h2>
			<div v-if="commentsEmpty">
				There are no comments for this rent a car shop
			</div>
			<div style="margin:20px;">
				<div v-for="c in comments" style="border-style: outset">
					<h3>{{c.customer.username}}</h3>
					<p style="font-size:18px; padding-left:20px">{{c.commentText}}</p>
					<p style="font-size:18px; padding-left:20px">Grade: {{c.grade}}/10</p>
				</div>
			</div>
		</div>
		</div>
	</div>
	`,
	
	mounted: function() {
    	this.id = this.$route.params.id
    	axios.get('rest/rentacar/'+this.id)
    	.then(response => {this.rentACar = response.data
    		if(this.rentACar.vehicles.length != 0){
				this.vehiclesEmpty = false
			}
    	})
    	setTimeout(() => {
        	this.loadComments()
        	this.loadMap()
        	this.formatOutput()
      	}, 200)
    },
	methods:{
		loadComments: function(){
			axios.get('rest/rentacar/comments/'+this.id)
    		.then(response => {this.comments = response.data
    			if(this.comments.length != 0){
					this.commentsEmpty = false
				}
    		})
		},
		formatOutput: function(){
				this.rentACar.location.longitude = this.rentACar.location.longitude.toFixed(2)
				this.rentACar.location.latitude = this.rentACar.location.latitude.toFixed(2)
				if(this.rentACar.startHour < 10){
					this.rentACar.startHour = '0'+this.rentACar.startHour
				}
				if(this.rentACar.startMinute < 10){
					this.rentACar.startMinute = '0'+this.rentACar.startMinute
				}
				if(this.rentACar.endHour < 10){
					this.rentACar.endHour = '0'+this.rentACar.endHour
				}
				if(this.rentACar.endMinute < 10){
					this.rentACar.endMinute = '0'+this.rentACar.endMinute
				}
		},
		loadMap: function(){
			this.map = new ol.Map({
	        target: 'map',
	        layers: [
	          new ol.layer.Tile({
	            source: new ol.source.OSM()
	          })
	        ],
	        view: new ol.View({
	          center: ol.proj.fromLonLat([this.rentACar.location.longitude, this.rentACar.location.latitude]),
	          zoom: 8
	        })
	      });
	      const layer = new ol.layer.Vector({
			source: new ol.source.Vector({
			    features: [
			    new ol.Feature({
			        geometry: new ol.geom.Point(ol.proj.fromLonLat([this.rentACar.location.longitude, this.rentACar.location.latitude])),
			    })
			    ]
			}),
			style: new ol.style.Style({
			    image: new ol.style.Icon({
			    anchor: [0.5, 1],
			    crossOrigin: 'anonymous',
			    src: 'https://docs.maptiler.com/openlayers/default-marker/marker-icon.png',
			    })
			})
			});
			this.map.addLayer(layer);
		}
	}
})