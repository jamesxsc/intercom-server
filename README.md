






## Note on Error Handling

- Internal/meaningful exceptions are thrown at the service level.
- These are caught at the controller level, where opaque exceptions with status codes (`ResponseStatusException`) are thrown.
- Meaningful exceptions can also be thrown at the controller level, provided they are annotated with `@ResponseStatus`.
