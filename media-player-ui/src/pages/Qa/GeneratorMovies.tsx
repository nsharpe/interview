import React, {ChangeEvent, useState} from "react";
import {seriesGeneratorControllerApi} from "../../api/qa-rest-client-factory";

const GeneratorUsers = ()=> {

    const [count, setCount] = useState<number>(1);
    const [result, setResult] = useState<any>(null);

    const handleNumberChange = (e: ChangeEvent<HTMLInputElement>) => {
        const value = parseInt(e.target.value, 10);
        setCount(isNaN(value) ? 0 : value);
    };

    const handleGenerateUser = async () => {
        try {
            const response = await seriesGeneratorControllerApi().movieGenerate(count);
            setResult(response.data);
        } catch (error) {
            setResult("Error: Check console or token");
        }
    };

    return (
        <div>
            <h3>Generate Movie</h3>

            <div>
                <label>
                    Number of Movie to generate:
                    <input
                        type="number"
                        value={count}
                        onChange={handleNumberChange}
                        min="1"
                        max="100"
                    />
                </label>

                <button
                    onClick={handleGenerateUser}
                >
                    Generate Movies
                </button>
            </div>

            {result && (
                <div>
                    <strong>Response:</strong>
                    <pre>
                        {JSON.stringify(result, null, 2)}
                    </pre>
                </div>
            )}
        </div>
    );
}

export default GeneratorUsers;