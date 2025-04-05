package org.bettacode.exampl_streams;

import org.bettacode.model.Department;
import org.bettacode.model.Employee;
import org.bettacode.model.Event;
import org.bettacode.model.Position;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;

//// of https://www.youtube.com/watch?v=RzEiCguFZiY
public class ex1CreateStreams {
    private List<Employee> emps = List.of(
            new Employee("Michael", "Smith",   243,  43, Position.CHEF),
            new Employee("Jane",    "Smith",   523,  40, Position.MANAGER),
            new Employee("Jury",    "Gagarin", 6423, 26, Position.MANAGER),
            new Employee("Jack",    "London",  5543, 53, Position.WORKER),
            new Employee("Eric",    "Jackson", 2534, 22, Position.WORKER),
            new Employee("Andrew",  "Bosh",    3456, 44, Position.WORKER),
            new Employee("Joe",     "Smith",   723,  30, Position.MANAGER),
            new Employee("Jack",    "Gagarin", 7423, 35, Position.MANAGER),
            new Employee("Jane",    "London",  7543, 42, Position.WORKER),
            new Employee("Mike",    "Jackson", 7534, 31, Position.WORKER),
            new Employee("Jack",    "Bosh",    7456, 54, Position.WORKER),
            new Employee("Mark",    "Smith",   123,  41, Position.MANAGER),
            new Employee("Jane",    "Gagarin", 1423, 28, Position.MANAGER),
            new Employee("Sam",     "London",  1543, 52, Position.WORKER),
            new Employee("Jack",    "Jackson", 1534, 27, Position.WORKER),
            new Employee("Eric",    "Bosh",    1456, 32, Position.WORKER)
    );

    private List<Department> deps = List.of(
            new Department(1, 0, "Head"),
            new Department(2, 1, "West"),
            new Department(3, 1, "East"),
            new Department(4, 2, "Germany"),
            new Department(5, 2, "France"),
            new Department(6, 3, "China"),
            new Department(7, 3, "Japan")
    );

    @Test
    public void creation() throws IOException {
        //Stream<String> lines = Files.lines(Paths.get("some.txt"));
        Stream<Path> list = Files.list(Paths.get("./"));
        Stream<Path> walk = Files.walk(Paths.get("./"));
        IntStream intStream = IntStream.of(1, 2, 3, 5);
        DoubleStream doubleStream = DoubleStream.of(12.2, 3, 2, 4.5);
        IntStream range = IntStream.range(10, 100); //10..99
        IntStream intStream1 = IntStream.rangeClosed(10, 100); //10..100
        int [] intsNumber={1, 2, 4,6,8};
        IntStream arrStream = Arrays.stream(intsNumber);
        Stream<String> stringStream = Stream.of("1", "3", "6");
        Stream<? extends Serializable> streamSerial = Stream.of(1, "3", "4");
        Stream<String> buildStr = Stream.<String>builder()
                .add("vlad")
                .add("Gena")
                .build();
        Stream<Department> streamDepartm = deps.stream();
        Stream<Event> generate1 = Stream.generate(() -> new Event(UUID.randomUUID(), LocalDateTime.now(), ""));
        Stream<Integer> iterate1 = Stream.iterate(1950, val -> val + 3);
        Stream<String> concat = Stream.concat(stringStream, buildStr);
    }
    @Test
    public void terminate(){
        long count = deps.stream().count();
        deps.stream().forEach(e-> System.out.println(e.getId()));
        deps.parallelStream().forEachOrdered(Department::getName);
        deps.stream().collect(Collectors.toList());
        deps.stream().toArray();
        Map<Integer, String> collectMap = deps.stream().collect(Collectors.toMap(
                Department::getId, //  тоже что и: emp -> emp.getId(),
                dep -> dep.getName()+"e!"

        ));
        IntStream intStream = IntStream.of(1, 3, 5);
        intStream.reduce((r, l)->r + l).orElse(0);
        //11:00 reduce for oblect departman

        IntStream.of(1, 2, 3).average();
        IntStream.of(1,2,5).max();
        IntStream.of(1,2,5).max();

        emps.stream().max((e1, e2)->e1.getAge() - e2.getAge());
        emps.stream().max(Comparator.comparingInt(Employee::getAge));
        emps.stream().findAny();
        emps.stream().findFirst();
        emps.stream().noneMatch(emp->emp.getAge()>50); //true
        emps.stream().anyMatch(emp->emp.getPosition()==Position.CHEF); //true
        emps.stream().allMatch((emp->emp.getAge()>18)); // true

    }
    @Test
    public void transform(){
        LongStream longStream = IntStream.of(1, 2, 3, 5).mapToLong(Long::valueOf);
        IntStream.of(1, 2, 3, 5).mapToObj(value -> new Event(UUID.randomUUID(), LocalDateTime.of(value, 12,11,1,0), ""));
        IntStream.of(1, 2, 3, 5, 1).distinct(); //1, 2, 3, 5
        IntStream intStream = IntStream.of(1, 2, 3, 5, 1).filter(num -> num > 1);
        Stream<Employee> employeeStream = emps.stream().filter(emp -> emp.getPosition() != Position.CHEF);
        emps.stream()
                .skip(3) //пропустить 3
                .limit(5); //оставить 5
        Stream<Integer> integerStream = emps.stream()
                .sorted((e1, e2) -> e1.getAge() - e2.getAge())
                .peek(e -> e.setAge(18))
                .map(e -> e.getId());
        emps.stream().takeWhile(emp->emp.getAge()>30).forEach(System.out::println); //брать элементы с начала, пока не выполнится условие
        emps.stream().dropWhile(emp->emp.getAge()<30).forEach(System.out::println); //пропускать элементы с начала, пока не выполнится условие
        //остальные все берутся

        IntStream.of(100, 200, 300)
                .flatMap(val->IntStream.of(val, val-50))
                .forEach(System.out::println);
    }
}
