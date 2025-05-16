// cypress/e2e/buy_argus_tank.cy.js
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

        // Wait for confirmation and go to cart
        cy.get('.message-success').should('contain', 'You added');
        cy.get('.showcart').click();

        // Wait for mini cart and click 'View and Edit Cart'
        cy.contains('View and Edit Cart').click();


// 2. Wait for the checkout button to become visible
        // Proceed to checkout
        cy.get('button[title="Proceed to Checkout"]')
            .filter(':visible')
            .click();

        cy.wait(5000)
        // Fill shipping form (use dummy data)
        cy.get('[name=username]').filter(':visible').type('john.doe@test.com');
        cy.get('[name=firstname]').type('John');
        cy.get('[name=lastname]').type('Doe');
        cy.get('[name="street\[0\]"]').type('123 Test Street');
        cy.get('[name=city]').type('Testville');
        cy.get('[name=region_id]').select('California');
        cy.get('[name=postcode]').type('90001');
        cy.get('[name=country_id]').select('United States');
        cy.get('[name=telephone]').type('1234567890');

        // Wait for shipping method to load and select the first option
        cy.get('input[name="ko_unique_1"]').check({ force: true });

        // Click Next
        cy.contains('Next').click();

        // Verify we're on the payment step
        cy.contains('Shipping').should('be.visible');
        cy.contains('Payment Method').should('be.visible');

        // ✅ STOP HERE - no payment details allowed on demo site
        // Optionally verify place order button exists
        cy.get('button[title="Place Order"]').click();

        cy.contains('h1.page-title', 'Thank you for your purchase!');

        // cy.get('h1').should('have.text', '\\n        Thank you for your purchase!    ')
    });
});
