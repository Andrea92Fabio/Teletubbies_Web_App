{{/*
=== WRAPPER PER FUNZIONI GLOBALI (NECESSARIO) ===
Queste funzioni permettono al subchart di accedere alle definizioni del Chart padre.
*/}}
{{- define "teletubbies-web-app.labels" -}}
{{- template "teletubbies-web-app.labels" . -}}
{{- end -}}

{{- define "teletubbies-web-app.selectorLabels" -}}
{{- template "teletubbies-web-app.selectorLabels" . -}}
{{- end -}}

{{- define "teletubbies-web-app.fullname" -}}
{{- template "teletubbies-web-app.fullname" . -}}
{{- end -}}


{{/*
Common labels (eredita e aggiunge componente)
*/}}
{{- define "db.labels" }}
{{- end }}

{{/*
Selector labels (eredita e aggiunge componente)
*/}}
{{- define "db.selectorLabels" }}
app.kubernetes.io/component: db
{{- end }}

{{/*
Nome del Chart.
*/}}
{{- define "db.name" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}
