const LogingIn = {template: '<logingPage></logingPage>'}
const RegisterPage = {template: '<registerPage></registerPage>'}

const router = new VueRouter({
	mode: 'hash',
	routes: [
		{path: '/', name: 'home', component: LogingIn},
		{path: '/register/', component: RegisterPage}
	]
})

var App = new Vue({
	router,
	el: '#contentDiv'
})