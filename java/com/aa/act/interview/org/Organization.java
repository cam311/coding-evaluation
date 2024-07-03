package com.aa.act.interview.org;

import java.util.Iterator;
import java.util.Optional;
import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;

public abstract class Organization {

    private Position root;
    private Random rand;
    
    public Organization() {
        root = createOrganization();
        this.rand = new Random();
    }
    
    protected abstract Position createOrganization();
    
    /**
     * hire the given person as an employee in the position that has that title
     * 
     * @param person
     * @param title
     * @return the newly filled position or empty if no position has that title
     */
    public Optional<Position> hire(Name person, String title) {
        this.rand = new Random();
        Queue<Position> queue = new LinkedList<>();

        queue.add(root);

        while(queue.peek() != null) {
            Position current = queue.poll();

            if(current.getTitle().compareTo(title) == 0 && !(current.isFilled())) {
                current.setEmployee(Optional.of(new Employee(rand.nextInt(1000), person)));
                return Optional.of(current);
            }
            else {
                Collection<Position> directReports = current.getDirectReports();
                Iterator<Position> iterator = directReports.iterator();

                while(iterator.hasNext()) {
                    queue.add(iterator.next());
                }
            }

        }

        return Optional.empty();
    }

    @Override
    public String toString() {
        return printOrganization(root, "");
    }
    
    private String printOrganization(Position pos, String prefix) {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
        for(Position p : pos.getDirectReports()) {
            sb.append(printOrganization(p, prefix + "  "));
        }
        return sb.toString();
    }
}
