Vue.component("rentACar",{
	data:function(){
		return{
			rentACars: null
		}
	},
	template: `
	<div>
	    <div v-for="r in rentACars" margin-bottom:10px style="border:1px solid black; font-size:20px; overflow: hidden; padding: 15px; width: 70%; margin: auto">
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
					Lokacija:
				</div>
				<div style="float: left; margin-left:10px">
			        <p>{{r.location.address}} <br> {{r.location.longitude}}, {{r.location.latitude}}</p>
		        </div>
		    	<p><br><br><br>Radno vreme: {{r.startHour}}:{{r.startMinute}} - {{r.endHour}}:{{r.endMinute}} {{r.status}}</p>
		    	<p>Ocena: {{r.grade}}/10</p>
	    	</div>
	    	
	    	<div style="overflow: hidden; padding: 40px;">
				<p>Vozila koja su u ponudi:</p>
	    	</div>
	    </div>
	 </div>
	`,
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