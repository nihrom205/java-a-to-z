package ru.job4j.tree;

import java.util.*;

/**
 * Class SimpleTreeApp.
 *
 * @author Alexey Rastorguev (rastorguev00@gmail.com)
 * @version 0.1
 * @since 29.12.2017
 */
public class SimpleTreeApp<E extends Comparable<E>> implements SimpleTree<E> {
    private Node<E> root;

    /**
     * коструктор
     * @param value значение элемента
     */
    public SimpleTreeApp(E value) {
        this.root = new Node<>(value);
    }

    /**
     * метод добавления элемента в дерево ищется parent и в него добавляется child.
     * @param parent родительский элемент
     * @param child дочерний элемент
     * @return true - добавлено, наче - false
     */
    @Override
    public boolean add(E parent, E child) {
        boolean isAdding = false;
        Optional<Node<E>> op = Optional.empty();
        op = findBy(parent);
        if (op.isPresent()) {
            if (!op.get().leaves().contains(new Node<>(child))) {
                op.get().add(new Node<>(child));
                isAdding = true;
            }
        }
        return isAdding;
    }

    /**
     * поиск значения в дереве.
     * @param value искомое значение
     * @return результат
     */
    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue((value))) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    /**
     * проверка двоичное дерево или нет.
     * @return true - двоичное, иначе - false.
     */
    public boolean isBinary() {
        boolean rezult = true;
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            int count = 0;
            Node<E> el = data.poll();
            for (Node<E> child : el.leaves()) {
                data.offer(child);
                count++;
            }
            if (count > 2) {
                rezult = false;
                break;
            }
        }
        return rezult;
    }

    /**
     * итератор.
     * @return итератор на дерево.
     */
    @Override
    public Iterator iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<Node<E>> {
        private Queue<Node<E>> listNode = new LinkedList<>();

        public Iter() {
            listNode.offer(root);
        }

        @Override
        public boolean hasNext() {
            return !listNode.isEmpty();
        }

        @Override
        public Node<E> next() {
            Node<E> rezult = listNode.poll();
            if (rezult != null) {
                for (Node<E> el : rezult.leaves()) {
                    listNode.offer(el);
                }
            }
            return rezult;
        }
    }

}
