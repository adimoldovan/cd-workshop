/// <reference types="Cypress" />

describe('Login test', function () {
    it('Log in with valid credentials', function () {
        cy.fixture('auth.json').then((user) => {
            cy.visit('login');
            cy.contains('Login');
            cy.get('[id="usernameOrEmail"]').type(user.username).should('have.value', user.username);
            cy.get('[name="password"]').type(user.password).should('have.value', user.password);
            cy.get('button').click();
        });
    });
});