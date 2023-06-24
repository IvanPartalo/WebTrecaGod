Vue.component("rentACarTemplate",{
	data:function(){
		return{
			rentACars: null,
			nameSearch: '',
			locationSearch: '',
			minGrade: 0
		}
	},
	template: `
	<div>
		<h1 style="text-align: center">Rent a car shops</h1>
		
		<h3 style="margin-left:20px">Search for specific shop</h3>
		<div style="margin-left:40px; margin-bottom:20px">
		<label>Name: </label><input type="text" v-model="nameSearch" style="margin-right:20px">
		<label>Location: </label><input type="text" v-model="locationSearch" style="margin-right:20px">
		<label>Minimum grade: </label><input type="text" v-model="minGrade" style="margin-right:20px">
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
			        <p>{{r.location.address}} <br> {{r.location.longitude}}, {{r.location.latitude}}</p>
		        </div>
		    	<p><br><br><br>Working time: {{r.startHour}}:{{r.startMinute}} - {{r.endHour}}:{{r.endMinute}} {{r.status}}</p>
		    	<p>Rating: {{r.grade}}/10</p>
	    	</div>
	    	
	    	<div style="overflow: hidden; padding: 40px;">
				<p>Vehicles in offer:</p>
	    	</div>
	    </div>
	 </div>
	`,
	computed:{
		rentACarList(){
			if(this.nameSearch.length > 0 || this.locationSearch.length > 0 || this.minGrade.length > 0){
				let filteredList = this.rentACars
				if (this.nameSearch.length > 0){
					filteredList = filteredList.filter((rentACar) => rentACar.name.toLowerCase().includes(
					this.nameSearch.toLowerCase()))
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
			return this.rentACars
		}
	},
	mounted: function() {
    	axios.get('rest/rentacar/')
    	.then(response => this.rentACars = response.data)
    },
	methods:{
		asd: function(){
			console.log(this.rentACars)
		}
	}
})