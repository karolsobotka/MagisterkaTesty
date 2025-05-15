// cypress/e2e/buy_nokia_lumia.cy.js

describe('Buy Nokia Lumia 1520 on Demoblaze', () => {
    it('should add Nokia Lumia 1520 to cart and place an order', () => {
        // Visit homepage
        cy.visit('https://www.demoblaze.com/index.html');

        // Click on "Phones" category (optional - can skip if it's default)
        cy.contains('Phones').click();

        // Click on Nokia Lumia 1520
        cy.contains('Nokia lumia 1520').click();

        // Verify product page loaded
        cy.get('.name').should('contain', 'Nokia lumia 1520');

        // Click 'Add to cart'
        cy.contains('Add to cart').click();

        // Handle alert
        cy.on('window:alert', (str) => {
            expect(str).to.equal('Product added');
        });

        // Wait for alert to appear and close
        cy.wait(1000);

        // Go to cart
        cy.contains('Cart').click();

        // Ensure the product is in the cart
        cy.get('td').contains('Nokia lumia 1520').should('be.visible');

        // Click on 'Place Order'
        cy.contains('Place Order').click();

        // Fill in the order form
        cy.get('#name').type('John Doe');
        cy.get('#country').type('USA');
        cy.get('#city').type('New York');
        cy.get('#card').type('1234 5678 9012 3456');
        cy.get('#month').type('12');
        cy.get('#year').type('2025');

        // Click 'Purchase'
        cy.contains('Purchase').click();

        // Check confirmation message
        cy.get('.sweet-alert').should('be.visible');
        cy.get('.sweet-alert h2').should('contain', 'Thank you for your purchase!');

        // Optionally, click OK to close
        cy.contains('OK').click();
    });
});
