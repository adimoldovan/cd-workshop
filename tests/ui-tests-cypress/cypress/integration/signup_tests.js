/// <reference types="Cypress" />

const url = 'signup';
const DEFAULT_NAME = 'Some Name';
const DEFAULT_USERNAME = 'some.username';
const DEFAULT_EMAIL = DEFAULT_USERNAME + '@mailinator.com';

describe('Sign up tests', function () {
    it.skip('Sign up with existing username', function () {
        cy.fixture('auth.json').then((user) => {
            cy.visit(url);
            cy.get('[name="name"]').type(DEFAULT_NAME).should('have.value', DEFAULT_NAME);
            cy.get('[name="username"]').type(user.username).should('have.value', user.username);
            cy.get('[name="email"]').type(DEFAULT_EMAIL).should('have.value', DEFAULT_EMAIL);
            cy.get('[name="password"]').type(user.password).should('have.value', user.password);
            cy.get('button').should('be.disabled');
            cy.contains('This username is already taken');
            cy.contains('Sign Up');
        });
    });

    it.skip('Sign up with existing email', function () {
        cy.fixture('auth.json').then((user) => {
            cy.visit(url);
            cy.get('[name="name"]').type(DEFAULT_NAME).should('have.value', DEFAULT_NAME);
            cy.get('[name="username"]').type(DEFAULT_USERNAME).should('have.value', DEFAULT_USERNAME);
            cy.get('[name="email"]').type(user.email).should('have.value', user.email);
            cy.get('[name="password"]').type(user.password).should('have.value', user.password);
            cy.get('button').should('be.disabled');
            cy.contains('This Email is already registered');
            cy.contains('Sign Up');
        });
    });

    it('Sign up with to short username', function () {
        cy.fixture('auth.json').then((user) => {
            cy.visit(url);
            cy.get('[name="name"]').type(DEFAULT_NAME).should('have.value', DEFAULT_NAME);
            cy.get('[name="username"]').type('ab').should('have.value', 'ab');
            cy.get('[name="email"]').type(DEFAULT_EMAIL).should('have.value', DEFAULT_EMAIL);
            cy.get('[name="password"]').type(user.password).should('have.value', user.password);
            cy.get('button').should('be.disabled');
            cy.contains('Username is too short (Minimum 3 characters needed.)');
            cy.contains('Sign Up');
        });
    });

    it('Sign up with to short name', function () {
        cy.fixture('auth.json').then((user) => {
            cy.visit(url);
            cy.get('[name="name"]').type('abc').should('have.value', 'abc');
            cy.get('[name="username"]').type(DEFAULT_USERNAME).should('have.value', DEFAULT_USERNAME);
            cy.get('[name="email"]').type(DEFAULT_EMAIL).should('have.value', DEFAULT_EMAIL);
            cy.get('[name="password"]').type(user.password).should('have.value', user.password);
            cy.get('button').should('be.disabled');
            cy.contains('Name is too short (Minimum 4 characters needed.)');
            cy.contains('Sign Up');
        });
    });

    it('Sign up with to invalid email - missing @', function () {
        cy.fixture('auth.json').then((user) => {
            cy.visit(url);
            cy.get('[name="name"]').type(DEFAULT_NAME).should('have.value', DEFAULT_NAME);
            cy.get('[name="username"]').type(DEFAULT_USERNAME).should('have.value', DEFAULT_USERNAME);
            cy.get('[name="email"]').type('abc').should('have.value', 'abc');
            cy.get('[name="password"]').type(user.password).should('have.value', user.password);
            cy.get('button').should('be.disabled');
            cy.contains('Email not valid');
            cy.contains('Sign Up');
        });
    });

    it('Sign up with to invalid email - missing domain', function () {
        cy.fixture('auth.json').then((user) => {
            cy.visit(url);
            cy.get('[name="name"]').type(DEFAULT_NAME).should('have.value', DEFAULT_NAME);
            cy.get('[name="username"]').type(DEFAULT_USERNAME).should('have.value', DEFAULT_USERNAME);
            cy.get('[name="email"]').type('abc@').should('have.value', 'abc@');
            cy.get('[name="password"]').type(user.password).should('have.value', user.password);
            cy.get('button').should('be.disabled');
            cy.contains('Email not valid');
            cy.contains('Sign Up');
        });
    });

    it('Sign up with to invalid email - missing local part', function () {
        cy.fixture('auth.json').then((user) => {
            cy.visit(url);
            cy.get('[name="name"]').type(DEFAULT_NAME).should('have.value', DEFAULT_NAME);
            cy.get('[name="username"]').type(DEFAULT_USERNAME).should('have.value', DEFAULT_USERNAME);
            cy.get('[name="email"]').type('@mailinator.com').should('have.value', '@mailinator.com');
            cy.get('[name="password"]').type(user.password).should('have.value', user.password);
            cy.get('button').should('be.disabled');
            cy.contains('Email not valid');
            cy.contains('Sign Up');
        });
    });
});