output "application_url" {
  description = "La URL pública para acceder a la aplicación Spring Boot."
  value       = "http://${aws_lb.main.dns_name}"
}