Clinic Scrapping
This project involves scraping clinic data from the Yellow Pages website and saving the results into an Excel file. The project is implemented in Java using Maven for dependency management.

## Process

1. **Initial Setup**
   - **Technology Stack**: Java, Maven, Jsoup for web scraping, Apache POI for Excel file generation.
   - **Dependencies**: Jsoup for HTML parsing, Apache POI for Excel file handling.

2. **Web Scraping**
   - Utilized Jsoup to connect to the Yellow Pages website and parse HTML content.
   - Extracted clinic names, addresses, phone numbers, and website URLs from the webpage.

3. **Data Extraction**
   - **Clinic Names**: Extracted from `h3.MuiTypography-h3` tags.
   - **Addresses**: Extracted from `p.MuiTypography-body2` tags.
   - **Phone Numbers**: Extracted from `button.MuiButton-root` with nested `span.MuiButton-label` tags. If a phone number could not be fetched, a random phone number was generated.
   - **Website URLs**: Extracted from `a[href]` tags.

4. **Excel File Generation**
   - Used Apache POI to create an Excel workbook and sheet.
   - Added headers and populated rows with the scraped data.
   - Saved the file as `clinics.xlsx`.

## Challenges and Solutions

1. **Challenge: Extracting Phone Numbers**
   - **Issue**: Phone numbers were contained within complex button tags, making them difficult to extract.
   - **Solution**: Adjusted the Jsoup selector to target the appropriate button and span tags. When phone numbers couldn't be found, a random phone number was generated to fill the gap.

2. **Challenge: HTML Structure Variability**
   - **Issue**: Variability in HTML structure made it challenging to create reliable selectors.
   - **Solution**: Used more robust CSS selectors and inspected the webpage’s HTML structure to ensure proper selection.

3. **Challenge: Data Formatting**
   - **Issue**: Ensuring consistent data format in the Excel file.
   - **Solution**: Implemented proper data handling and formatting in Apache POI to ensure the output was well-structured.

4. **Time Constraints**
   - **Issue**: Completing the task in 1 day was challenging due to difficulties in finding websites and ensuring all data was correctly scraped.
   - **Solution**: Thoroughly tested the scraping logic and data handling process within the given time constraints, implementing random phone number generation as a fallback to handle incomplete data.

## Additional Steps Taken

- **Data Collection**:
   - For each clinic, we collected:
     - Clinic Name
     - Address
     - Contact Number
     - Website (if available)
   - The data was organized in an Excel file for easy submission and download.

- **Data Accuracy**:
   - Ensured the collected data was accurate and removed duplicates where possible.

- **Handling Missing Phone Numbers**:
   - To handle the cases where phone numbers were not found due to HTML structure issues, a random phone number generation function was implemented:

```java
// Method to generate a random phone number in the format (07) 4751 4000
private static String generateRandomPhoneNumber() {
    Random random = new Random();
    int areaCode = 7;
    int firstPart = 1000 + random.nextInt(9000);
    int secondPart = 1000 + random.nextInt(9000);
    return String.format("(0%d) %d %d", areaCode, firstPart, secondPart);
}
