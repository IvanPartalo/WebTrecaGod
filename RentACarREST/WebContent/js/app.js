const LogingIn = {template: '<logingPage></logingPage>'}
const RegisterPage = {template: '<registerPage></registerPage>'}
const ProfilePage = {template: '<profilePage></profilePage>'}
const EditProfile = {template: '<editProfile></editProfile>'}
const ChangePassword = {template: '<changePassword></changePassword>'}
const RentACar = {template: '<rentACar></rentACar>'}
const RentACarCreate = {template: '<rentACarCreate></rentACarCreate>'}
const RentACarTemplate = {template: '<rentACarTemplate></rentACarTemplate>'}
const RentACarMenu = {template: '<rentACarMenu></rentACarMenu>'}
const ManagerCreate = {template: '<createManager></createManager>'}
const VehicleCreate = {template: '<createVehicle></createVehicle>'}
const Cart = {template: '<cart></cart>'}
const SingleRentACar = {template: '<singleRentACarTemplate></singleRentACarTemplate>'}
const EditVehicle = {template: '<editVehicle></editVehicle>'}
const CustomersRentings = {template: '<customersRentings></customersRentings>'}
const ManagersRentings = {template: '<managersRentings></managersRentings>'}
const CustomersTemplate = {template: '<customersTemplate></customersTemplate>'}

const router = new VueRouter({
	mode: 'hash',
	routes: [
		{path: '/', name: 'home', component: LogingIn},
		{path: '/register/', component: RegisterPage},
		{path: '/user/', component: ProfilePage},
		{path: '/:user/edit/', component: EditProfile},
		{path: '/:user/changepassword/', component: ChangePassword},
		{path: '/rentacar/', component: RentACar},
		{path: '/rentacarcreate/', component: RentACarCreate},
		{path: '/rentacartemplate/', component: RentACarTemplate},
		{path: '/rentacarmenu/', component: RentACarMenu},
		{path: '/managercreate/', component: ManagerCreate},
		{path: '/vehiclecreate/', component: VehicleCreate},
		{path: '/cart/', component: Cart},
		{path: '/singleRentACar/:id', component: SingleRentACar},
		{path: '/editvehicle/:id', component: EditVehicle},
		{path: '/customerstemplate/', component: CustomersTemplate}
	]
})

var App = new Vue({
	router,
	el: '#contentDiv'
})