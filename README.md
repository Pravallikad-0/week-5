# Company Project API

A Spring Boot 3 service that demonstrates managing companies, projects, employees, and their addresses with fully bidirectional JPA mappings, cascading rules, pagination, sorting, and Specification-driven queries.

## Highlights

- Company → Project (1:N) with cascading delete that also removes associated employees and addresses.
- Employee ↔ Address (1:1) with orphan removal and cascaded persistence/update/delete.
- Employee ↔ Project (M:N) defaulted to `LAZY` fetching; observe SQL count vs `EAGER` by flipping the fetch type on `Project.employees` and watching Hibernate logs.
- Specification/HQL-based repository methods so that controllers query parent entities while Hibernate auto-joins related data.
- Seed data (`data.sql`) loads one company with 3 projects and 22 employees (20+ on project Phoenix) for realistic testing.
- Pageable + sortable endpoints accepting dynamic query parameters (e.g. `?page=0&size=10&sort=lastName,asc;firstName,desc`).

## Tech Stack

- Java 17, Spring Boot 3.3.2
- Spring Data JPA with H2 in-memory database
- Lombok (annotation processed in Maven)
- Hibernate statistics & SQL logging enabled for query inspection

## Getting Started

```bash
cd company-project-api
./mvnw spring-boot:run
```

H2 console is available at `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:companydb`).

### Observing Lazy vs. Eager Loading

1. Start with the default `LAZY` configuration on `Project.employees` (see `entity/Project.java`).
2. Hit `GET /api/projects/1/employees` and watch the console: you should see batched select statements due to lazy loading + joins produced by Specifications.
3. Switch the annotation to `@ManyToMany(fetch = FetchType.EAGER)` and restart. Repeat the request and note the increased SQL statements and larger result set due to immediate fetching.
4. Document your findings (e.g., `LAZY` issued 2 queries vs `EAGER` issuing N additional selects) in your own test notes if needed.

### Preloaded Sample Data

`src/main/resources/data.sql` inserts:

- Company **Acme Holding Group** with projects Phoenix, Atlas, Horizon.
- 22 employees (Phoenix has 20+ members) each with a unique address.
- Project assignments across all projects for testing pagination and cascading rules.

## API Reference (all responses are JSON)

### Employees

| Verb | Path | Description |
|------|------|-------------|
| `POST` | `/api/employees` | Create employee + address + project links (address saved automatically).
| `PUT` | `/api/employees/{id}` | Update employee and embedded address record atomically.
| `GET` | `/api/employees/{id}` | Fetch employee + address + projects (uses entity graph for eager fetch).
| `DELETE` | `/api/employees/{id}` | Removes employee and cascading address only (projects remain).
| `GET` | `/api/employees/{id}/projects` | Pageable list of projects assigned to the employee.

**Sample Request**

```json
{
  "firstName": "Test",
  "lastName": "User",
  "email": "test.user@example.com",
  "jobTitle": "QA Engineer",
  "companyId": 1,
  "projectIds": [1, 2],
  "address": {
    "street": "1 Infinite Loop",
    "city": "Austin",
    "state": "TX",
    "postalCode": "73301",
    "country": "USA"
  }
}
```

### Projects

| Verb | Path | Description |
|------|------|-------------|
| `GET` | `/api/projects/{projectId}/employees` | Paginated employees on a project with optional sorting.
| `DELETE` | `/api/projects/{projectId}` | Deletes a project but keeps employees intact, only clearing join-table rows.

### Companies

| Verb | Path | Description |
|------|------|-------------|
| `GET` | `/api/companies/search?name=Acme Holding Group` | Returns company info + projects + employees per project via a single parent query.
| `DELETE` | `/api/companies/{id}` | Cascades removal to projects, employees, and addresses in one request.

**Company Response Structure**

```json
{
  "id": 1,
  "name": "Acme Holding Group",
  "description": "Sample company with multiple delivery streams",
  "projects": [
    {
      "id": 1,
      "name": "Phoenix",
      "employees": [
        { "id": 1, "firstName": "Liam", "lastName": "Bennett", "jobTitle": "Engineering Lead", "email": "liam.bennett@example.com" }
        // ...more employees
      ]
    }
  ]
}
```

## Pagination & Sorting

- `page` (default `0`) and `size` (default `20`).
- `sort` accepts multiple comma-separated segments joined by `;`, e.g., `sort=lastName,asc;firstName,desc`.
- Applied uniformly through `PaginationUtils` to keep repository calls efficient.

## Testing

Run the unit/integration compilation checks via:

```bash
./mvnw clean test
```

During manual API tests, monitor the console SQL output to validate lazy vs eager behavior and the cascading delete operations described in the scenario.

