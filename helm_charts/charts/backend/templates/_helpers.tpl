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
{{- define "backend.labels" -}}
{{ include "teletubbies-web-app.labels" . }}
{{- end }}

{{/*
Selector labels (eredita e aggiunge componente)
*/}}
{{- define "backend.selectorLabels" -}}
{{ include "teletubbies-web-app.selectorLabels" . }}
app.kubernetes.io/component: backend
{{- end }}

{{/*
Fullname per il subchart Backend
*/}}
{{- define "backend.fullname" -}}
{{- printf "%s-backend" (include "teletubbies-web-app.fullname" .) | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Nome del Chart.
*/}}
{{- define "backend.name" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}