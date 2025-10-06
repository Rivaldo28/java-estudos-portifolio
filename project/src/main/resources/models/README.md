# NLP Models Directory

This directory is intended to store the Natural Language Processing (NLP) models required for the chatbot.

## Required Models

1. `en-token.bin` - English tokenizer model
2. `en-doccat.bin` - Document categorization model for intent recognition

## How to Get the Models

You can download pre-trained OpenNLP models from the Apache OpenNLP website or train custom models for your specific use case.

### Pre-trained Models
Download pre-trained models from Apache OpenNLP:
- https://opennlp.apache.org/models.html

### Training Custom Models
For better performance, you may want to train custom models tailored to your chatbot's domain.

## Notes

The application is designed to work even without these models by falling back to simpler algorithms, but for best results, proper NLP models should be placed in this directory.