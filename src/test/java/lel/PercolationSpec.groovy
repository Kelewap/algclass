package lel

import spock.lang.Specification

class PercolationSpec extends Specification {

    def "initial 1-grid state"() {
        given:
        def percolation = new Percolation(1)

        expect:
        !percolation.isOpen(1, 1)
        !percolation.isFull(1, 1)
        percolation.numberOfOpenSites() == 0
        !percolation.percolates()
    }

    def "full 1-grid state"() {
        given:
        def percolation = new Percolation(1)

        when:
        percolation.open(1, 1)

        then:
        percolation.isOpen(1, 1)
        percolation.isFull(1, 1)
        percolation.numberOfOpenSites() == 1
        percolation.percolates()
    }

    def "initial 2-grid state"() {
        given:
        def percolation = new Percolation(2)

        expect:
        !percolation.isOpen(1, 1)
        !percolation.isFull(1, 1)
        percolation.numberOfOpenSites() == 0
        !percolation.percolates()
    }

    def "test for 2 size grid"() {
        given:
        def percolation = new Percolation(2)

        when:
        percolation.open(1, 1)

        then:
        percolation.isOpen(1, 1)
        percolation.isFull(1, 1)
        percolation.numberOfOpenSites() == 1
        !percolation.percolates()

        when:
        percolation.open(2, 2)

        then:
        percolation.isOpen(1, 1)
        !percolation.isFull(2, 2)
        percolation.numberOfOpenSites() == 2
        !percolation.percolates()
    }

    def "2-size grid with bottom open"() {
        given:
        def percolation = new Percolation(2)

        when:
        percolation.open(2, 1)
        percolation.open(2, 2)

        then:
        !percolation.percolates()
    }
}
