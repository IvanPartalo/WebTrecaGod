Vue.component("rentACarCreate",{
	data:function(){
		return{
			rentACarDTO: {name:"", address:"", longitude:"", latitude:"", beginWorkTime:null, endWorkTime:null},
			errorMessage: "",
			managers: null,
			selectedManager: null,
			id:-1,
			buttonText: "Create!",
			additionalInfo: "ds"
		}
	},
	template: `
	<div >
		<h1 style="width:400px; margin:auto">Create new rent a car</h1>
		<div style="width:480px; margin:auto; font-size:18px">
			<form>
				<div style="border:1px solid black; margin:5px">
					<div style="margin:10px">
						<label>Name*</label>
						<input type="text" v-model="rentACarDTO.name" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>Address*</label>
						<input type="text" v-model="rentACarDTO.address" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>longitude*</label>
						<input v-model="rentACarDTO.longitude" type="text" style="float:right; font-size:17px"><br>
					</div>
					<div style="margin:10px">
						<label>latitude*</label>
						<input type="text" v-model="rentACarDTO.latitude" style="float:right; font-size:17px"><br>
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
				<p>{{additionalInfo}}</p>
				<div style="width:80px;">
					<input type="submit" v-bind:value="buttonText" v-on:click="create" style="background-color:powderblue; font-size:20px;">
				</div>
				<br>
				
				<p v-if="errorMessage.length" style="color:red; width:200px; margin:auto">{{errorMessage}}</p>
			</form>
		</div>
	</div>
	`,
	mounted(){
		axios.get('rest/users/freeManagers').
		then(response => (this.managers = response.data))
		setTimeout(() => {
        	this.setButtonText()
      	}, 200)
	},
	methods:{
		setButtonText: function(){
			if(this.managers.length == 0)
			{
				this.buttonText = "Create and add new manager"
				this.additionalInfo = "There is no free managers, you will need to add new one."
			}
			
		},
		create: function(){
			event.preventDefault()
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