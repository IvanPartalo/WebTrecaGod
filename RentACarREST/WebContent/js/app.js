const LogingIn = {template: '<logingPage></logingPage>'}
const RegisterPage = {template: '<registerPage></registerPage>'}
const ProfilePage = {template: '<profilePage></profilePage>'}
const EditProfile = {template: '<editProfile></editProfile>'}

const router = new VueRouter({
	mode: 'hash',
	routes: [
		{path: '/', name: 'home', component: LogingIn},
		{path: '/register/', component: RegisterPage},
		{path: '/:customer/:id', component: ProfilePage},
		{path: '/:customer/edit/:id', component: EditProfile}
	]
})

var App = new Vue({
	router,
	el: '#contentDiv'
})