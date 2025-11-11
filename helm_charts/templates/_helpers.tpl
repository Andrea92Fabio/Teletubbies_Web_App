{{/*
Create a default fully qualified app name.
We truncate at 63 chars because of K8s name limits.
If release name contains chart name it will be used as a full name.
*/}}
{{- define "teletubbies-web-app.fullname" -}}
{{- if .Values.global.nameOverride -}}
{{- printf "%s-%s" .Release.Name .Values.global.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-teletubbies-web-app" .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}

{{/*
Common labels
*/}}
{{- define "teletubbies-web-app.labels" -}}
helm.sh/chart: {{ include "teletubbies-web-app.name" . }}
{{- range $key, $value := .Values.global.labels }}
{{ $key }}: {{ $value | quote }}
{{- end }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "teletubbies-web-app.selectorLabels" -}}
{{- range $key, $value := .Values.global.labels }}
{{ $key }}: {{ $value | quote }}
{{- end }}
{{- end }}

{{/*
Chart Name
*/}}
{{- define "teletubbies-web-app.name" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}