Vue.component("cart",{
	data:function(){
		return{
			cart : {vehicles : null, user : null, price : null}
		}
	},
	template: `
	<div>
		<div>
			<label>Total price:</label>
			<b><label>{{this.cart.price}}</label></b><br>
		</div>
		<div v-for="(v,index) in this.cart.vehicles" style="border:1px solid black; font-size:21px; padding: 10px; width: 70%; margin: 0% 12% 1% 12%; background-color: #FBD603">
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
							<button v-on:click="removeFromCart(v.id, v.price, index)">Remove from cart!</button>
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
		removeFromCart : function(id, p, index){
			axios.post('rest/users/removeFromCart/'+ id).then(response => (this.cart.vehicles.splice(index, 1), 
			this.cart.price-=p))
		}
	}
})