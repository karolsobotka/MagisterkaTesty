Cypress.on('uncaught:exception', (err, runnable) => {
    return false;
});

describe('Buy Argus All-Weather Tank from Magento demo store', () => {
    it('should add the Argus tank to cart and proceed to checkout', () => {

        cy.visit('https://magento.softwaretestingboard.com/');

        cy.get('#search').type('Argus{enter}');
        cy.get('a.product-item-link').contains('Argus All-Weather Tank').click();

        cy.get('[option-label="M"]').click();
        cy.get('[option-label="Gray"]').click();

        cy.get('#product-addtocart-button').click();

        cy.get('.message-success').should('contain', 'You added');
        cy.get('.showcart').click();

        cy.contains('View and Edit Cart').click();

        cy.get('button[title="Proceed to Checkout"]')
            .filter(':visible')
            .click();

        cy.wait(5000)
        cy.get('[name=username]').filter(':visible').type('john.doe@test.com');
        cy.get('[name=firstname]').type('John');
        cy.get('[name=lastname]').type('Doe');
        cy.get('[name="street\[0\]"]').type('123 Test Street');
        cy.get('[name=city]').type('Testville');
        cy.get('[name=region_id]').select('California');
        cy.get('[name=postcode]').type('90001');
        cy.get('[name=country_id]').select('United States');
        cy.get('[name=telephone]').type('1234567890');

        cy.get('input[name="ko_unique_1"]').check({ force: true });

        cy.contains('Next').click();

        cy.contains('Shipping').should('be.visible');
        cy.contains('Payment Method').should('be.visible');

        cy.get('button[title="Place Order"]').click();

        cy.contains('h1.page-title', 'Thank you for your purchase!');
    });
});
