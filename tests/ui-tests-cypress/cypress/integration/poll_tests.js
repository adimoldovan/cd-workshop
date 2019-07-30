/// <reference types="Cypress" />

describe('Create a poll test', function () {
    it('Log in and create a poll', function () {
        cy.fixture('auth.json').then((user) => {
            cy.visit('login');
            cy.contains('Login');
            cy.get('[id="usernameOrEmail"]').type(user.username).should('have.value', user.username);
            cy.get('[name="password"]').type(user.password).should('have.value', user.password);
            cy.get('button').click();
        });

        cy.fixture('poll.json').then((poll) => {
            cy.get('[href="/poll/new"]').click();
            cy.get('[name="question"]').type(poll.question).should('have.value', poll.question);
            cy.get('[placeholder="Choice 1"]').type(poll.choices[0]).should('have.value', poll.choices[0]);
            cy.get('[placeholder="Choice 2"]').type(poll.choices[1]).should('have.value', poll.choices[1]);
            cy.get('button.create-poll-form-button').click();
        });
    });
});