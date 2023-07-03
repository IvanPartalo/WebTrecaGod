Vue.component("cart",{
	data:function(){
		return{
			cart : {vehicles : null, user : null, price : null, prepairedPurchases:null},
			purchase:{startDateTime:"", endDateTime:"" }
		}
	},
	template: `
	<div>
		<div class="row" style="font-size:22px">
			<div class="column">
				<label>Total price:</label>
				<b><label>{{this.cart.price}} euros</label></b><br>
			</div>
			<div class="column">
				<button style="font-size:20px" v-on:click="rent()">Rent!</button>
				<button style="font-size:18px" v-on:click="goBack()">Go back!</button>
			</div>
		</div>
		
	    <div v-for="(p, ip) in this.cart.prepairedPurchases">
	    	<div>
		    	<label>Start date: </label>
		    	<b><label>{{p.start.dayOfMonth}}-{{p.start.month}}-{{p.start.year}}</label></b>
			    <label style="margin-left:20px">Duration: </label>
			    <b><label>{{p.duration}} hours</label></b>
			    <label style="margin-left:20px">Price for this time: </label>
			    <b><label>{{p.price}} euros</label></b>
	    	</div>
		    <div v-for="(v,index) in p.vehicles" style="border:1px solid black; font-size:21px; padding: 10px; width: 70%; margin: 0% 12% 1% 12%; background-color: #FBD603">
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
							<div align="right">
								<button v-on:click="removeFromCart(v.id, v.price, index, ip)">Remove from cart!</button>
							</div>
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
		axios.get('rest/users/cart')
    	.then(response => this.cart = response.data)
    },
	methods:{
		removeFromCart : function(id, p, index, ip){
			//this.cart.vehicles.splice(0,this.cart.vehicles.length)
			//axios.get('rest/rentacar/vehicles')
    		//.then(response => this.cart.vehicles = response.data)
    		this.purchase.startDateTime = this.cart.prepairedPurchases[ip].startDateTime
    		this.purchase.endDateTime = this.cart.prepairedPurchases[ip].endDateTime
			axios.post('rest/users/removeFromCart/'+ id, this.purchase).then(response => this.remove(p, index, ip))
		},
		remove(p, index, ip){
			this.cart.prepairedPurchases[ip].vehicles.splice(index, 1)
			this.cart.price-=p
			this.cart.prepairedPurchases[ip].price-=p
			if(this.cart.prepairedPurchases[ip].vehicles.length == 0){
				this.cart.prepairedPurchases.splice(ip,1)
			}
		},
		rent : function(){
			if(this.cart.vehicles == null || this.cart.vehicles.length == 0){
				alert("The cart is empty!")
				return
			}
			axios.post('rest/users/rent').then(response => ( router.push(`/user/`)))
		},
		goBack : function(){
			router.push(`/user/`)
		}
	}
})